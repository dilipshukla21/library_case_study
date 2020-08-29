package com.fis.learning.ms.subscriber.service;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fis.learning.ms.subscriber.model.Book;

@FeignClient(name="library-api-gateway-service")
@RibbonClient(name="book-service")
public interface BookService {

	@GetMapping("book-service/books")
    public List<Book> getBooks();
	
	@GetMapping("book-service/books/{bookId}")
    public Book getBook(@PathVariable String bookId);
	
	@PutMapping("book-service/books/{bookId}")
    public boolean updateBookStock(@PathVariable String bookId, @RequestBody Book book);
}
