package com.fis.learning.ms.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fis.learning.ms.model.Book;
import com.fis.learning.ms.service.BookService;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookServiceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceController.class);

	@Autowired
	private BookService service;

	@GetMapping("/books")
	@ResponseBody
	public ResponseEntity<List<Book>> retrieveBooks() {
		return ResponseEntity.ok(service.getBooks());
	}
	
	@GetMapping("/books/{bookId}")
	@ResponseBody
	public ResponseEntity<Book> retrieveBook(@PathVariable("bookId") String bookId) {
		return service.getBook(bookId)
				.map(book->ResponseEntity.ok(book))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/books/{bookId}")
	@ResponseBody
	public ResponseEntity<Boolean> update(@PathVariable("bookId") String bookId, @RequestBody Book book) {
		LOGGER.info("Issue/return request for book with id {} is made with quantity as {}", bookId, book.getCopiesAvailable());
		
		try {
			service.update(bookId, book.getCopiesAvailable());
		}catch (Exception e) {
			LOGGER.error("Not able to issue/return book. Error:: ", e);
			return ResponseEntity.ok(Boolean.FALSE);
		}
		return ResponseEntity.ok(Boolean.TRUE);
	}

}
