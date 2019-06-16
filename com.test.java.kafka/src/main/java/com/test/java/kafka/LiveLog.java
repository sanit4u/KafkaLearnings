package com.test.java.kafka;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "timeMillis", "thread", "level", "loggerName", "message", "endOfBatch", "loggerFqcn", "threadId",
		"threadPriority" })
public class LiveLog {

	@JsonProperty("timeMillis")
	private Object timeMillis;
	@JsonProperty("thread")
	private Object thread;
	@JsonProperty("level")
	private Object level;
	@JsonProperty("loggerName")
	private Object loggerName;
	@JsonProperty("message")
	private String message;
	@JsonProperty("endOfBatch")
	private Object endOfBatch;
	@JsonProperty("loggerFqcn")
	private Object loggerFqcn;
	@JsonProperty("threadId")
	private Object threadId;
	@JsonProperty("threadPriority")
	private Object threadPriority;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("timeMillis")
	public Object getTimeMillis() {
		return timeMillis;
	}

	@JsonProperty("timeMillis")
	public void setTimeMillis(Object timeMillis) {
		this.timeMillis = timeMillis;
	}

	@JsonProperty("thread")
	public Object getThread() {
		return thread;
	}

	@JsonProperty("thread")
	public void setThread(Object thread) {
		this.thread = thread;
	}

	@JsonProperty("level")
	public Object getLevel() {
		return level;
	}

	@JsonProperty("level")
	public void setLevel(Object level) {
		this.level = level;
	}

	@JsonProperty("loggerName")
	public Object getLoggerName() {
		return loggerName;
	}

	@JsonProperty("loggerName")
	public void setLoggerName(Object loggerName) {
		this.loggerName = loggerName;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("endOfBatch")
	public Object getEndOfBatch() {
		return endOfBatch;
	}

	@JsonProperty("endOfBatch")
	public void setEndOfBatch(Object endOfBatch) {
		this.endOfBatch = endOfBatch;
	}

	@JsonProperty("loggerFqcn")
	public Object getLoggerFqcn() {
		return loggerFqcn;
	}

	@JsonProperty("loggerFqcn")
	public void setLoggerFqcn(Object loggerFqcn) {
		this.loggerFqcn = loggerFqcn;
	}

	@JsonProperty("threadId")
	public Object getThreadId() {
		return threadId;
	}

	@JsonProperty("threadId")
	public void setThreadId(Object threadId) {
		this.threadId = threadId;
	}

	@JsonProperty("threadPriority")
	public Object getThreadPriority() {
		return threadPriority;
	}

	@JsonProperty("threadPriority")
	public void setThreadPriority(Object threadPriority) {
		this.threadPriority = threadPriority;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}