package springkafkabasics.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

public class KafkaConsumerConfig {
	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	private static final String SASL_PROTOCOL = "SASL_SSL";
	private static final String SCRAM_SHA_256 = "SCRAM-SHA-256";
	private final String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
	private final String prodJaasCfg = String.format(jaasTemplate, "cyy3wd7r", "eAQPX5G290PkZ5CKo6drJKvqHqO6FA66");

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// allows a pool of processes to divide the work of consuming and processing
		// records
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "cyy3wd7r-consumers");
		// automatically reset the offset to the earliest offset
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		props.put("sasl.mechanism", SCRAM_SHA_256);
		props.put("sasl.jaas.config", prodJaasCfg);
		props.put("security.protocol", SASL_PROTOCOL);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() 
	{
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

}
