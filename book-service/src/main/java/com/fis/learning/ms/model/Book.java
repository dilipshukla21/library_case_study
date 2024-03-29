package com.fis.learning.ms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class Book {
	@Id
	@Column(name="book_id",updatable=false)
	private String bookId;
	
	@Column(name="book_name",unique= true, nullable=false)
    private String name;
	
	@Column(name="author",nullable=false)
    private String author;
	
	@Min(0)
	@Column(name="available_copies")
    private Integer copiesAvailable;
	
	@Min(0)
	@Column(name="total_copies")
    private Integer totalCopies;
	
	public Book() {
		this(null, null, null, null, null);
	}
	
	public Book(String bookId, String name, String author, Integer copiesAvailable, Integer totalCopies) {
		super();
		this.bookId = bookId;
		this.name = name;
		this.author = author;
		this.copiesAvailable = copiesAvailable;
		this.totalCopies = totalCopies;
	}
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getCopiesAvailable() {
		return copiesAvailable;
	}
	public void setCopiesAvailable(Integer copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
	public Integer getTotalCopies() {
		return totalCopies;
	}
	public void setTotalCopies(Integer totalCopies) {
		this.totalCopies = totalCopies;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", name=" + name + ", author=" + author + ", copiesAvailable="
				+ copiesAvailable + ", totalCopies=" + totalCopies + "]";
	}
}
