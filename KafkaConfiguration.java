package com.test.spring.boot.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);
//
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

//	@Value("${apache.kafka.producer.properties}")
//	private String kafkaProducerProperties;

	@Value("${kafka.topic.test}")
	private String topic;

	@Autowired
	private ApplicationContext context;

	@Bean
	public Map<String, Object> producerConfigs() {

		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}

	@Bean
	public ProducerFactory<?, ?> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<?, ?> kafkaTemplate() {
		KafkaTemplate<?, ?> kafkaTemplate = new KafkaTemplate<>(producerFactory());
		kafkaTemplate.setDefaultTopic(this.topic);
		return kafkaTemplate;
	}
}
