package com.test.spring.boot.service;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;


/**
 * This class builds the kafka stream for processing the Enrollment Verification
 * notification.
 * 
 * @author rsa
 *
 */
@Component
public class EnrollmentNotificationStream {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${kafka.topic.event.enrollment.verification}")
	private String enrollmentVerficationEventtopic;

	@Value("${kafka.topic.email}")
	private String outEventtopic;

	@Autowired
	private Gson gson;

// to call separate stream configured bean, we need to use @Qualifier("the corresponding stream builder name")
// in case you want to call the default stream builder(DONT use the qualifer).
// eg. public KStream<String, String> kStreamDefault( StreamsBuilder streamsBuilder) 
	@Bean("enrollmentStreamTopology")
	public KStream<String, String> kStreamEnrollment(
			@Qualifier("enrollmentStreamBuilder") StreamsBuilder streamsBuilder) {
		KStream<String, String> enrollmentCompletionEventStream = null;
		try {
			enrollmentCompletionEventStream = streamsBuilder.stream("EnrollmentCompleteEvent",
					Consumed.with(Serdes.String(), Serdes.String()));

      // DO THE MAP/FLATMAP/GROUP/AGGREGATION		

			// writing to the output topic
			enrollmentEmailEventStream.to(outEventtopic);

		} catch (Exception e) {
			log.error("Error while building the kafka streams for enrollment verification notification. ", e);
		}

		return enrollmentCompletionEventStream;
	}

}
