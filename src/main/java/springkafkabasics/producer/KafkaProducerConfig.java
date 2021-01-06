package springkafkabasics.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaProducerConfig {
	
	@Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
	//private static final String TRUSTSTORE_JKS = "/var/private/ssl/kafka.client.truststore.jks"; 
	private static final String SASL_PROTOCOL = "SASL_SSL"; 
	private static final String SCRAM_SHA_256 = "SCRAM-SHA-256"; 
	private final String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";"; 
	private final String prodJaasCfg = String.format(jaasTemplate, "cyy3wd7r", "eAQPX5G290PkZ5CKo6drJKvqHqO6FA66");

	 @Bean 
	 public ProducerFactory<String, String> producerFactory() { 
	   final Map<String, Object> configProps = new HashMap<>(); 
	   configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress); 
	   configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); 
	   configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); 
	   /*
	   configProps.put(ProducerConfig.ACKS_CONFIG, "all"); 
	   configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "cid1"); 
	   configProps.put(ProducerConfig.RETRIES_CONFIG, 0);
	   configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 1000);
	   configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	   configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	   */
	   configProps.put("sasl.mechanism", SCRAM_SHA_256); 
	   configProps.put("sasl.jaas.config", prodJaasCfg); 
	   configProps.put("security.protocol", SASL_PROTOCOL); 
	   //configProps.put("ssl.truststore.location", TRUSTSTORE_JKS); 
	   //configProps.put("ssl.truststore.password", "password"); 
	   //configProps.put("ssl.endpoint.identification.algorithm", ""); 
	   return new DefaultKafkaProducerFactory<>(configProps); 
	 }

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
