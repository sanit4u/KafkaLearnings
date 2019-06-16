    
package com.test.spring.boot.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConcurrentKafkaListenerContainerFactory<String, String> listnerContainerFactory;

//
	/**
	 * Consumes from <TopicName>
	 * 
	 * @Header refers to the custom header of the message. Since, Kafka is agnostic
	 *         to the message types. Means, what ever you send into the topic, it
	 *         sends as String/JSON/AVRO messages. but for handler/Listner/consumer,
	 *         it may important to know what kind of message its going to handle. In
	 *         order to specify, the message type, we can use {@link RecordHeader}
	 *         to add custom Headers. Here, EVENT_TYPE is used as the custom header
	 *         and the value would bet event type class.
	 * 
	 * 
	 * @param message
	 * @param payloadType
	 */
	@KafkaListener(topics = <TopicName String>)
	public void consume(@Payload String message, @Header("EVENT_TYPE") String payloadType) {
		System.out.println("payload Type : [" + payloadType + "]  Consumed message: " + message);

	}


	public Set<String> getAllTopics() {
		try (Consumer<String, String> consumer = (Consumer<String, String>) listnerContainerFactory.getConsumerFactory()
				.createConsumer()) {
			Map<String, List<PartitionInfo>> map = consumer.listTopics();
			return map.keySet();
		}
	}

}
