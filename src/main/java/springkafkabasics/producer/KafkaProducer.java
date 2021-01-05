package springkafkabasics.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {
	
	Logger logger = LoggerFactory.getLogger(KafkaProducer.class.getName());
	
	@Autowired
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	@Value(value = "${target.topic}")
    private String topicName;
	
	KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

	public void sendMessage(String msg) {
	    kafkaTemplate.send(topicName, msg,topicName);
	    logger.info("message {} sent to topic {}",msg,topicName);
	    
	}
	
	public void sendMessageWithCallback(String message) {
        
	    ListenableFuture<SendResult<String, String>> future = 
	      kafkaTemplate.send(topicName, message);
		
	    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

	        @Override
	        public void onSuccess(SendResult<String, String> result) {
	        	logger.info("Sent message=[{}] with offset=[{}]",message,result.getRecordMetadata().offset());
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	        	logger.info("Unable to send message=[{} ] due to : {}",message,ex.getMessage());
	        }
	    });
	}

}
