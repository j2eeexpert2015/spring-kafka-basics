package springkafkabasics.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
	
	
	@KafkaListener(topics = "${target.topic}", groupId="${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  Consumed message: " + message);
    }

}
