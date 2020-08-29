package com.fis.learning.ms.subscriber.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fis.learning.ms.subscriber.model.Book;
import com.fis.learning.ms.subscriber.model.BookNotFoundException;
import com.fis.learning.ms.subscriber.model.BookServiceNotAvailableException;
import com.fis.learning.ms.subscriber.model.BookStoreNotUpdatedException;
import com.fis.learning.ms.subscriber.model.Subscription;
import com.fis.learning.ms.subscriber.repository.SubscriptionRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class SubscriptionService {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private SubscriptionRepository repository;
	
	public List<Subscription> getSubScriptions() {
		return repository.findAll();
	}

	public Subscription getSubscription(String subscriberName) {
		return repository.findById(subscriberName).orElse(null);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public Subscription subscribe(Subscription subscription) throws BookNotFoundException {
		Subscription createdSubscription = repository.save(subscription);
		updateBookAvailability(subscription, true);
		repository.flush();
		return createdSubscription;
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public boolean unsubscribe(Subscription subscription) {
		Subscription dbSubscription = repository.findBySubscriberName(subscription.getSubscriberName())
				.stream()
				.filter(s->s.getBookId().equals(subscription.getBookId()))
				.findFirst().orElse(null);
		if(dbSubscription == null) {
			throw new IllegalArgumentException("Book with Id "+subscription.getBookId() + 
					"not issued to subscriber " + subscription.getSubscriberName());
		}
		
		repository.save(subscription);
		updateBookAvailability(subscription, false);
		repository.flush();
		return true;
	}

	@HystrixCommand(fallbackMethod = "bookServiceFallBack")
	private void updateBookAvailability(Subscription subscription, boolean issueBook) throws BookNotFoundException {
		Book bookToSubscribe = bookService.getBook(subscription.getBookId());
		if(Objects.isNull(bookToSubscribe) || (issueBook && bookToSubscribe.getCopiesAvailable()<=0)) {
			throw new BookNotFoundException("No book found for id " + subscription.getBookId());
		}
		
		Integer newAvailableQuantity = issueBook ? (bookToSubscribe.getCopiesAvailable().intValue() - 1) 
				: (bookToSubscribe.getCopiesAvailable().intValue() + 1);
		if(newAvailableQuantity > bookToSubscribe.getTotalCopies()) {
			throw new BookStoreNotUpdatedException("Book store has already all copies of book with Id " + subscription.getBookId());
		}
		bookToSubscribe.setCopiesAvailable(newAvailableQuantity);
		boolean update = bookService.updateBookStock(bookToSubscribe.getBookId(), bookToSubscribe);
		if(!update) {
			throw new BookStoreNotUpdatedException("Book store not update for Id " + subscription.getBookId());
		}
	}
	
	@SuppressWarnings("unused")
	private void bookServiceFallBack(Subscription subscription, boolean issue) {
		throw new BookServiceNotAvailableException("Book service is down. Try after some time. "
				+ "Subscription is unssucessful for subscriber "+subscription.getSubscriberName());
	}
}
