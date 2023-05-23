package com.interview.prep.service;

import com.interview.prep.exception.BookAlreadyExistsException;
import com.interview.prep.exception.BookNotFoundException;
import com.interview.prep.model.Book;
import com.interview.prep.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) throws BookAlreadyExistsException {
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new BookAlreadyExistsException("Book already exists");
        }
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public Book updateBook(Long id, Book book) throws BookNotFoundException {
        return bookRepository.findById(id)
                .map(p -> {
                    p.setTitle(book.getTitle());
                    p.setPublisher(book.getPublisher());
                    p.setIsbn(book.getIsbn());
                    p.setEditionYear(book.getEditionYear());
                    return bookRepository.save(p);
                })
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

}

