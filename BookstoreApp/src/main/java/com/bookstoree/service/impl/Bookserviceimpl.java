package com.bookstoree.service.impl;

import com.bookstoree.model.Book;
import com.bookstoree.repository.Booksrepository;
import com.bookstoree.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Bookserviceimpl implements BookService {
    private final Booksrepository booksrepository;

    @Override
    public void saveBook(Book book)
    {
        booksrepository.save(book);
    }

    @Override
    public void deleteBook(Long id)
    {
        booksrepository.deleteById(id);
    }

    @Override
    public void updateBook(Book book) {
        booksrepository.save(book);
    }
}
