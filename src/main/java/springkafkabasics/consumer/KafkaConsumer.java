package springkafkabasics.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * The Kafka multiple consumer configuration involves following classes:
 * DefaultKafkaConsumerFactory : is used to create new Consumer instances where all consumer share common configuration 
 * properties mentioned in this bean.
 * ConcurrentKafkaListenerContainerFactory : is used to build ConcurrentMessageListenerContainer. 
 * This factory is primarily for building containers for @KafkaListener annotated methods.
 * ConsumerConfig : holds the consumer configuration keys.
 * KafkaListener : marks a method to be the target of a Kafka message listener on the specified topics
 * 
 *
 */
@Component
public class KafkaConsumer {
	
	
	@KafkaListener(topics = "${target.topic}", groupId="${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  Consumed message: " + message);
    }

}
