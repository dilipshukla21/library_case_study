package com.fis.learning.ms.subscriber.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class BookStoreNotUpdatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookStoreNotUpdatedException() {
		super();
	}

	public BookStoreNotUpdatedException(String message) {
		super(message);
	}

}
