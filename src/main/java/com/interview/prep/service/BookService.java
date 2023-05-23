package com.interview.prep.service;

import com.interview.prep.exception.BookAlreadyExistsException;
import com.interview.prep.exception.BookNotFoundException;
import com.interview.prep.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book createBook(Book book) throws BookAlreadyExistsException;
    Book getBookById(Long id) throws BookNotFoundException;
    Book updateBook(Long id, Book book) throws BookNotFoundException;
    void deleteBook(Long id);
}
