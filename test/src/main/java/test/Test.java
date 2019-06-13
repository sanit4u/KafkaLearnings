package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	private static final Logger logger = LoggerFactory.getLogger(Test.class);

	public static void main(String[] argv) {
		// Configure logger
		logger.debug("Debug message from HelloKafkaLogger.main,");
		logger.info("Info message from HelloKafkaLogger.main");
		logger.warn("Warn message from HelloKafkaLogger.main");
		logger.error("Error message from HelloKafkaLogger.main");
//		LogManager.shutdown();
	}

}
