package com.fis.learning.ms.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fis.learning.ms.model.Book;
import com.fis.learning.ms.repository.BookRepository;

@Service
public class BookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepository bookStore;
	
	public List<Book> getBooks() {
		return bookStore.findAll();
	}
	
	public Optional<Book> getBook(String bookId) {
		return bookStore.findById(bookId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public Boolean update(String bookId, Integer copiesAvailableCount) {
		LOGGER.info("Update for book with id {} and passed quantity is {}", bookId, copiesAvailableCount);
		try {
			Book dbBook = bookStore.getOne(bookId);
			if(Objects.nonNull(dbBook)) {
				dbBook.setCopiesAvailable(copiesAvailableCount);
				bookStore.save(dbBook);
				bookStore.flush();
			}
		}catch (Exception e) {
			LOGGER.error("Not able to issue/return book. Error:: ", e);
			throw e;
		}
		return Boolean.TRUE;
	}



}
