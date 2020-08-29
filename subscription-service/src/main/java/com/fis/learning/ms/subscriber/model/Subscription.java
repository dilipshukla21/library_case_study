package com.fis.learning.ms.subscriber.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import io.micrometer.core.instrument.util.StringUtils;

@Entity
public class Subscription implements Serializable {
	private static final long serialVersionUID = 5101766372851913443L;
	
	private static final String DATE_FORMAT = "dd-MMM-yyyy";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

	@Id
	@Column(name="subscriber_name", updatable=false, nullable=false)
	private String subscriberName;
	
	@Column(name="date_subscribed", updatable=false, columnDefinition = "DATE")
	@DateTimeFormat(pattern = DATE_FORMAT)
	private LocalDate dateSubscribed;
	
	@Column(name="date_returned", columnDefinition = "DATE")
	@DateTimeFormat(pattern = DATE_FORMAT)
	private LocalDate dateReturned;
	
	@Column(name="book_id", nullable=false)
	private String bookId;
	
	public Subscription() {
		super();
	}
	
	public Subscription(String subscriberName, LocalDate dateSubscribed, LocalDate dateReturned, String bookId) {
		super();
		this.subscriberName = subscriberName;
		this.dateSubscribed = dateSubscribed;
		this.dateReturned = dateReturned;
		this.bookId = bookId;
	}
	
	public Subscription(String subscriberName, String dateSubscribed, String dateReturned, String bookId) {
		super();
		this.subscriberName = subscriberName;
		this.dateSubscribed = parseDate(dateSubscribed);
		this.dateReturned = parseDate(dateReturned);
		this.bookId = bookId;
	}
	
	public String getSubscriberName() {
		return subscriberName;
	}
	
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	
	public LocalDate getDateSubscribed() {
		return dateSubscribed;
	}
	
	public void setDateSubscribed(String dateSubscribed) {
		this.dateSubscribed = parseDate(dateSubscribed);
	}
	
	public LocalDate getDateReturned() {
		return dateReturned;
	}
	
	public void setDateReturned(String dateReturned) {
		this.dateReturned = parseDate(dateReturned);
	}
	
	public String getBookId() {
		return bookId;
	}
	
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((dateReturned == null) ? 0 : dateReturned.hashCode());
		result = prime * result + ((dateSubscribed == null) ? 0 : dateSubscribed.hashCode());
		result = prime * result + ((subscriberName == null) ? 0 : subscriberName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscription other = (Subscription) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (dateReturned == null) {
			if (other.dateReturned != null)
				return false;
		} else if (!dateReturned.equals(other.dateReturned))
			return false;
		if (dateSubscribed == null) {
			if (other.dateSubscribed != null)
				return false;
		} else if (!dateSubscribed.equals(other.dateSubscribed))
			return false;
		if (subscriberName == null) {
			if (other.subscriberName != null)
				return false;
		} else if (!subscriberName.equals(other.subscriberName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscription [subscriberName=" + subscriberName + ", dateSubscribed=" + dateSubscribed
				+ ", dateReturned=" + dateReturned + ", bookId=" + bookId + "]";
	}
	
	private static LocalDate parseDate(String dateStr) {
		if(StringUtils.isBlank(dateStr)) {
			return null;
		}
		try {
		 return LocalDate.parse(dateStr, DATE_FORMATTER);
		}catch(Exception e) {
			throw new IllegalArgumentException("Incorrect format", e);
		}
	}
    
	
}
