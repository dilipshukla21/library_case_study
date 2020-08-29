package com.fis.learning.ms.subscriber.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fis.learning.ms.subscriber.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
	
	  List<Subscription> findBySubscriberName(String subscriberName);
}


