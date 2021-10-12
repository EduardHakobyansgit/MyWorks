package com.bookstoree.service;

import com.bookstoree.model.Book;

public interface BookService {
    void saveBook(Book book);
    void deleteBook(Long id);
    void updateBook(Book book);

}
