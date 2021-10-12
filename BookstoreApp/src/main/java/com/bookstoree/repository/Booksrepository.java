package com.bookstoree.repository;

import com.bookstoree.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface Booksrepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);

}
