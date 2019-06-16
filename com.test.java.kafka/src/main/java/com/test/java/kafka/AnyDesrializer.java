package com.test.java.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AnyDesrializer implements Deserializer<AnyObject> {

	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	public AnyObject deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		AnyObject object = null;
		try {
			object = mapper.readValue(data, AnyObject.class);
		} catch (Exception exception) {
			System.out.println("Error while deserializing " + exception);
		}
		return object;
	}

	public void close() {
	}
}