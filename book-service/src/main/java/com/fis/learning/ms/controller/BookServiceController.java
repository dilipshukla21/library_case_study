package com.fis.learning.ms.controller;

import java.util.List;

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
import com.fis.learning.ms.repository.BookRepository;

@RestController
public class BookServiceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceController.class);

	@Autowired
	private BookRepository bookStore;

	@GetMapping("/books")
	@ResponseBody
	public ResponseEntity<List<Book>> retrieveBooks() {
		List<Book> books = bookStore.findAll();
		return ResponseEntity.ok(books);
	}
	
	@GetMapping("/books/{bookId}")
	public ResponseEntity<Book> retrieveBook(@PathVariable("bookId") String bookId) {
		return bookStore.findById(bookId)
				.map(book->ResponseEntity.ok(book))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/books/{bookId}")
	public ResponseEntity<Boolean> update(@PathVariable("bookId") String bookId, @RequestBody Book book) {
		LOGGER.info("Update for book with id {} and passed quantity is {}", bookId, book.getCopiesAvailable());
		try {
			if(bookStore.existsById(bookId)) {
				bookStore.saveAndFlush(book);
			}
		}catch (Exception e) {
			LOGGER.error("Not able to issue/return book. Error:: ", e);
			return ResponseEntity.ok(Boolean.FALSE);
		}
		return ResponseEntity.ok(Boolean.TRUE);
	}

}
