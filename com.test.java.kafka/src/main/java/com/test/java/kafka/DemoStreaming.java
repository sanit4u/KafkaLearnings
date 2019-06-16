package com.test.java.kafka;

import java.util.Properties;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

public class DemoStreaming {

	public static void main(String[] args) {

		streaming();
	}

	private static void streaming() {

		KafkaStreams streams = createUserIdentityStreamingInstance();

		try {
			streams.start();

		} catch (Exception e) {
			System.out.println("Error while streaming");
			System.out.println(e);
		}

	}

	private static KafkaStreams createUserIdentityStreamingInstance() {

		StreamsBuilder builder = new StreamsBuilder();
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "DemoStreaming");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);

		KStream<byte[], byte[]> user_identityRaw = builder.stream("postgres.public.user_identity");

		user_identityRaw.mapValues(data -> {

			System.out.println("->>>>>> " + new String(data));
			return data;
		});

		return new KafkaStreams(builder.build(), props);
	}
}
