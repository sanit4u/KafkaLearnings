package de.fraunhofer.fokus.smile.services.auth.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import de.fraunhofer.fokus.smile.services.events.infra.Event;
import de.fraunhofer.fokus.smile.services.events.infra.EventDispatcher;
import de.fraunhofer.fokus.smile.services.register.core.aspect.TrackTime;

// NOTE : To send the postgres data we can use kafka source connector
// https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-1/
@Component
public class KafkaSender2 implements EventDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSender2.class);

	@Autowired
	private Gson gson;

	@Autowired
	private KafkaTemplate<String, String> commandProducer;

	@Value("${kafka.topic.test}")
	private String topic;

	@TrackTime
	@Override
	public <T extends Event> void dispatch(T event) {
		try {

			/*
			 * Here the commands could have sent to their respective command topics, But the
			 * ordering of the commands wont be persisted, because the ordering cannot be
			 * attained among inter-topics. But can be attain intra-topic. It means, the
			 * commands when published into same topic can be ordered. Hence, did not use
			 * different topic names for different command.
			 */
			// String topic = Constants.CommandConstants.COMMAND_PREFIX +
			// command.getClass().getSimpleName();

			/*
			 * When using Kafka, you can preserve the order of those events by putting them
			 * all in the same partition. In this example, you would use the "the command
			 * class name "as the partitioning key, and then put all these different
			 * Commands in the same topic. They must be in the same topic because different
			 * topics mean different partitions, and ordering is not preserved across
			 * partitions.
			 */
			String key = event.getClass().getSimpleName();
			String value = this.gson.toJson(event);
			LOGGER.info("Sent event :" + value);
			ProducerRecord<String, String> record = new ProducerRecord<>(this.topic, key, value);
			RecordHeader header = new RecordHeader("EVENT_TYPE", key.getBytes());
			record.headers().add(header);
			commandProducer.send(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
