package com.fis.learning.ms.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fis.learning.ms.subscriber.controller.SubscriptionController;
import com.fis.learning.ms.subscriber.model.Book;
import com.fis.learning.ms.subscriber.model.Subscription;
import com.fis.learning.ms.subscriber.service.BookService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SubscriptionControllerTest {
	
	@Autowired
	private SubscriptionController controller;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restClient;
	
	@MockBean
	private BookService bookService;
	
	private String baseURL;
	
	@BeforeEach
	public void setup() {
		baseURL = "http://localhost:" + port + "/subscribers";
	}
	
	@Test
	public void controllerLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void getSubscriptions() {
		List<Subscription> books = restClient.getForObject(baseURL+"?subscriberName=", List.class);
		assertThat(books).hasSize(3);
	}
	
	@Test
	public void getSubscription() {
		List<Subscription> subscriptions = restClient.getForObject(baseURL+"?subscriberName=John", List.class);
		assertThat(subscriptions).hasSize(1);
		assertThat(subscriptions.get(0).getSubscriberName()).isEqualTo("John");
		assertThat(subscriptions.get(0).getBookId()).isEqualTo("B1212");
	}
	
	@Test
	public void subscribe() {
		Mockito.when(bookService.getBook("B4232")).thenReturn(new Book("B4232","Language Fundamentals","H S Parkmay",5,5));
		Mockito.when(bookService.updateBookStock("B4232", 
				new Book("B4232","Language Fundamentals","H S Parkmay",4,5))).thenReturn(Boolean.TRUE);
		ResponseEntity<?> response = restClient.postForObject(baseURL, 
				new Subscription("Dilip", LocalDate.of(2020, 8, 30), null,"B4232"), ResponseEntity.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Subscription subscription = (Subscription)response.getBody();
		assertThat(subscription.getBookId()).isEqualTo("B4232");
		assertThat(subscription.getSubscriberName()).isEqualTo("Dilip");
	}
	
	@Test
	public void unsubscribe() {
		Mockito.when(bookService.getBook("B1212")).thenReturn(new Book("B1212","Language Fundamentals","H S Parkmay",0,2));
		Mockito.when(bookService.updateBookStock("B1212", 
				new Book("B1212","Language Fundamentals","H S Parkmay",1,2))).thenReturn(Boolean.TRUE);
		ResponseEntity<?> response = restClient.postForObject(baseURL, 
				new Subscription("John", LocalDate.of(2020, 6, 12), LocalDate.of(2020, 8, 30),"B1212"), ResponseEntity.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}


}
