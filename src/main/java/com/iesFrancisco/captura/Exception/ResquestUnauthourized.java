package com.iesFrancisco.captura.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ResquestUnauthourized extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String ex;
	private Object f;
	
	public ResquestUnauthourized(String ex, Object f) {
		super(ex+ " - "+f);
		this.ex = ex;
		this.f = f;
	}
	public ResquestUnauthourized(String ex) {
		super(ex);
		this.ex = ex;
	}
	
	public String getExceptionDetail() {
		return ex;
	}
	public Object getFieldValue() {
		return f;
	}
}
