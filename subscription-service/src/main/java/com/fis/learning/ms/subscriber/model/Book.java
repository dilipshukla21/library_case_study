package com.fis.learning.ms.subscriber.model;

public class Book {

	private final String bookId;

    private final String name;
	
    private final String author;
	
    private Integer copiesAvailable;

    private final Integer totalCopies;
	
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
	
	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
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
	
	/*public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setTotalCopies(Integer totalCopies) {
		this.totalCopies = totalCopies;
	}
*/
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", name=" + name + ", author=" + author + ", copiesAvailable="
				+ copiesAvailable + ", totalCopies=" + totalCopies + "]";
	}
}
