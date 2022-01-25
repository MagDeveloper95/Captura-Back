package com.iesFrancisco.captura.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class RecordOk extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String ex;
	private Object f;
	
	public RecordOk(String ex, Object f) {
		super(ex+ " - "+f);
		this.ex = ex;
		this.f = f;
	}
	
	public String getExceptionDetail() {
		return ex;
	}
	public Object getFieldValue() {
		return f;
	}
}
