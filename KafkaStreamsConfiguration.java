package com.test.spring.boot.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.FailOnInvalidTimestamp;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

//	#earliest for replay all the messages into the broker
//	#largest for reading only the latest messages committed to the kafka logs
//	#this should depends on the use-case of data/notification handling
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String offset;

	@Value("${spring.kafka.consumer.string.appid}")
	private String consumerAppId;

	@Value("${spring.kafka.consumer.string.groupid.group.preregister}")
	private String groupNotificationEventConsumerGroupId;

	@Value("${spring.kafka.consumer.string.groupid.enrollment}")
	private String enrollmentEventConsumerGroupId;

	@Value("${spring.kafka.consumer.string.groupid.livelog}")
	private String liveLogEventConsumerGroupId;

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public StreamsConfig config() {
		return new StreamsConfig(primaryConfig());
	}

	public void setDefaults(Map<String, Object> streamConfiguration) {
		streamConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		streamConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		streamConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		streamConfiguration.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, FailOnInvalidTimestamp.class);
		streamConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);
	}

	@Primary
	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME)
	public FactoryBean<StreamsBuilder> factoryBean() {
		StreamsBuilderFactoryBean streamsBuilderFactoryBean = new StreamsBuilderFactoryBean();
		streamsBuilderFactoryBean.setStreamsConfig(config());
		return streamsBuilderFactoryBean;
	}

	private Map<String, Object> primaryConfig() {
		Map<String, Object> config = new HashMap<>();
		setDefaults(config);
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, consumerAppId + ".group.notification1");
		config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 30000);
		config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
		config.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
		return config;
	}

	// use this to build other bean to build stream builder
	@Bean(name = "enrollmentStreamBuilder")
	public FactoryBean<StreamsBuilder> groupStreamBuilderFactoryBean() {
		StreamsBuilderFactoryBean streamsBuilderFactoryBean = new StreamsBuilderFactoryBean();
		streamsBuilderFactoryBean.setStreamsConfig(new StreamsConfig(enrollmentConfig()));
		return streamsBuilderFactoryBean;
	}


	private Map<String, Object> enrollmentConfig() {
		Map<String, Object> config = new HashMap<>();
		setDefaults(config);
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, enrollmentEventConsumerGroupId + "1.stream");
		config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 30000);
		config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
		config.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
		return config;
	}

	// use this to build other bean to build stream builder
	@Bean(name = "liveLogStreamBuilder")
	public FactoryBean<StreamsBuilder> liveLogStreamBuilderFactoryBean() {
		StreamsBuilderFactoryBean streamsBuilderFactoryBean = new StreamsBuilderFactoryBean();
		streamsBuilderFactoryBean.setStreamsConfig(new StreamsConfig(liveLogConfig()));
		return streamsBuilderFactoryBean;
	}


	private Map<String, Object> liveLogConfig() {
		Map<String, Object> config = new HashMap<>();
		setDefaults(config);
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, liveLogEventConsumerGroupId + "3.stream");
		config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 30000);
		config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, new JsonSerde<>(LiveLog.class).getClass());
		config.put(JsonDeserializer.DEFAULT_VALUE_TYPE, LiveLog.class);
		config.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
		return config;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
