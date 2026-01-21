package com.ai.common;

import java.util.Map;

public class ResponseBean {
	private String statusCode;
	private String statusDesc;
	private Map<Object, String> data;
	private String error;
	private Map<Object, String> errors;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Map<Object, String> getData() {
		return data;
	}

	public void setData(Map<Object, String> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<Object, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<Object, String> errors) {
		this.errors = errors;
	}

	public ResponseBean(String statusCode, String statusDesc, Map<Object, String> data, String error,
			Map<Object, String> errors) {
		super();
		this.statusCode = statusCode;
		this.statusDesc = statusDesc;
		this.data = data;
		this.error = error;
		this.errors = errors;
	}

	public ResponseBean() {
		super();
	}
}