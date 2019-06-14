package com.demo.test.elk.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class DemoController {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private static long NUMBER_REQUEST = 0;
	private static long err_NUMBER_REQUEST = 0;
	private static long warn_NUMBER_REQUEST = 0;

	public DemoController() {
		super();
	}

	@GetMapping(path = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsers() {

		LOG.info("get users is called :" + NUMBER_REQUEST);
		NUMBER_REQUEST++;
		return ResponseEntity.ok("Called getUsers");
	}

	@GetMapping(path = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsersd() {

		LOG.error("error users is called :" + err_NUMBER_REQUEST);
		err_NUMBER_REQUEST++;
		return ResponseEntity.ok("Called getUsers");
	}

	@GetMapping(path = "/warn", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsersf() {

		LOG.warn("warn users is called :" + warn_NUMBER_REQUEST);
		warn_NUMBER_REQUEST++;
		return ResponseEntity.ok("Called getUsers");
	}

}