package com.fis.learning.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fis.learning.ms.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByName(String name);
}