package com.fis.learning.ms.subscriber.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fis.learning.ms.subscriber.model.Subscription;
import com.fis.learning.ms.subscriber.service.SubscriptionService;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscriptionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);
	
	@Autowired
	private SubscriptionService service;

	@GetMapping("/subscribers")
	public ResponseEntity<List<Subscription>> getSubscription(@QueryParam("subscriberName") String subscriberName) {
		
		if(StringUtils.isEmpty(subscriberName)) {
			LOGGER.info("Requesting data of all suscribers");
			return ResponseEntity.ok(service.getSubScriptions());
		}
		LOGGER.info("Requesting data of suscriber with name {}", subscriberName);
		Subscription subscription = service.getSubscription(subscriberName);
		if(Objects.isNull(subscription)) {
			throw new IllegalArgumentException("No subscription found with subscriber name " + subscriberName);
		}
		return ResponseEntity.ok(Collections.singletonList(subscription));
	}
	
	@PostMapping("/subscribers")
	public ResponseEntity<?> subscribe(@RequestBody Subscription subscription) {
		LOGGER.info("Starting subscription with detail {}", subscription);
		
		if(Objects.isNull(subscription.getDateReturned())) {
			Subscription createdEntity = service.subscribe(subscription);
			LOGGER.info("Successfully made subscription for subscriber {}", subscription.getSubscriberName());
			return ResponseEntity.created(URI.create("/subscribers/"+ createdEntity.getSubscriberName())).body(createdEntity);
		}else {
			service.unsubscribe(subscription);
			LOGGER.info("Successfully returned book with id {} for subscriber {} ", subscription.getBookId(), 
					subscription.getSubscriberName());
		}
		return ResponseEntity.ok().build();
	}
}
