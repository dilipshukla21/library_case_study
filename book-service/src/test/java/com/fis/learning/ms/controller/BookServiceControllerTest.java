package com.fis.learning.ms.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.fis.learning.ms.model.Book;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookServiceControllerTest {
	
	@Autowired
	private BookServiceController controller;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restClient;
	
	private String baseURL;
	
	@BeforeEach
	public void setup() {
		baseURL = "http://localhost:" + port + "/books";
	}
	
	@Test
	public void controllerLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void getBooks() {
		List<Book> books = restClient.getForObject(baseURL, List.class);
		
		assertThat(books).hasSize(2);
	}
	
	@Test
	public void getBook() {
		Book book = restClient.getForObject(baseURL+"/B4232", Book.class);
		assertThat(book.getBookId()).isEqualTo("B4232");
		assertThat(book.getTotalCopies()).isEqualTo(5);
	}
	
	@Test
	public void updateBook() {
		restClient.put(baseURL+"/B4232", new Book("B4232","Language Fundamentals","H S Parkmay",4,5));
 		Book book = restClient.getForObject(baseURL+"/B4232", Book.class);
		assertThat(book.getBookId()).isEqualTo("B4232");
		assertThat(book.getCopiesAvailable()).isEqualTo(4);
	}

}
