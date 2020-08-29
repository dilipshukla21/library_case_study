package com.fis.learning.ms.subscriber.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.FAILED_DEPENDENCY)
public class BookServiceNotAvailableException extends RuntimeException {
	private static final long serialVersionUID = 2L;

	public BookServiceNotAvailableException() {
		super();
	}

	public BookServiceNotAvailableException(String message) {
		super(message);
	}

}
