package com.test.java.kafka;

import java.io.Serializable;

public class AnyObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4181918589006646162L;

	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
