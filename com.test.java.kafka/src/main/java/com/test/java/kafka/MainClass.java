package com.test.java.kafka;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainClass {

	public static void main(String[] args) {
//		runProducer();
		runConsumer();
	}

	static void runConsumer() {
		Consumer<String, String> consumer = ConsumerBuilder.buildConsumer();

		int noMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				noMessageToFetch++;
				if (noMessageToFetch > KafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
					break;
				else
					continue;
			}

			consumerRecords.forEach(record -> {
				System.out.println("Record Key " + record.key());
				System.out.println("Record value " + record.value());
				System.out.println("Record partition " + record.partition());
				System.out.println("Record offset " + record.offset());
			});
			consumer.commitAsync();
		}
		consumer.close();
	}

	static void runConsumerAnyObject() {
		Consumer<String, AnyObject> consumer = ConsumerBuilder.buildConsumerAnyObject();

		int noMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<String, AnyObject> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				noMessageToFetch++;
				if (noMessageToFetch > KafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
					break;
				else
					continue;
			}

			consumerRecords.forEach(record -> {
				System.out.println("Record Key " + record.key());
				System.out.println("Record value " + record.value());
				System.out.println("Record partition " + record.partition());
				System.out.println("Record offset " + record.offset());
			});
			consumer.commitAsync();
		}
		consumer.close();
	}

	static void runProducer() {
		Producer<String, String> producer = ProducerBuilder.buildProducer();
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		objectMapper.setDateFormat(format);

		for (int index = 0; index < KafkaConstants.MESSAGE_COUNT; index++) {
			String value = "This is record " + index;
			System.out.println(value);
			try {
				AnyObject dummyGroupEvent = getDummyAnyObjectEvent(index);
				String jsonValue = objectMapper.writeValueAsString(dummyGroupEvent);
				System.out.println(jsonValue);
				final ProducerRecord<String, String> record = new ProducerRecord<String, String>(
						KafkaConstants.TOPIC_NAME, KafkaConstants.TOPIC_NAME, jsonValue);

				RecordMetadata metadata = producer.send(record).get();
				System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
						+ " with offset " + metadata.offset());
			} catch (ExecutionException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			} catch (InterruptedException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static void runProducerObject() {
		Producer<String, AnyObject> producer = ProducerBuilder.buildProducerAnyObject();
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		objectMapper.setDateFormat(format);

		for (int index = 0; index < KafkaConstants.MESSAGE_COUNT; index++) {
			String value = "This is record " + index;
			System.out.println(value);
			try {
				AnyObject dummyGroupEvent = getDummyAnyObjectEvent(index);
				final ProducerRecord<String, AnyObject> record = new ProducerRecord<String, AnyObject>("LOG_DEMO",
						KafkaConstants.TOPIC_NAME, dummyGroupEvent);

				RecordMetadata metadata = producer.send(record).get();
				System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
						+ " with offset " + metadata.offset());
			} catch (ExecutionException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			} catch (InterruptedException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			}
		}
	}

	private static AnyObject getDummyAnyObjectEvent(int index) {
		AnyObject anyObject = new AnyObject();
		anyObject.setId(String.valueOf(index));
		anyObject.setName(String.valueOf(index) + "-Name");
		return anyObject;
	}
}
