package com.interview.prep.service;

import com.interview.prep.exception.BookAlreadyExistsException;
import com.interview.prep.exception.BookNotFoundException;
import com.interview.prep.model.Book;
import com.interview.prep.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllbooks() {
        List<Book> books = Arrays.asList(
                new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1"),
                new Book(1L,"Book Title2", "Author Name2", 2021, "ISBN123456799", "publisher2")
        );
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        Assert.assertEquals(2, result.size());
        Assert.assertEquals("Book Title1", result.get(0).getTitle());
        Assert.assertEquals(2023, result.get(0).getEditionYear());
        Assert.assertEquals("Book Title2", result.get(1).getTitle());
        Assert.assertEquals(2021, result.get(1).getEditionYear());
    }

    @Test
    public void testCreatebook() throws Exception{
        Book book = new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1");
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        Book result = bookService.createBook(book);

        Assert.assertEquals("Book Title1", result.getTitle());
        Assert.assertEquals(2023, result.getEditionYear());
    }

    @Test(expected = BookAlreadyExistsException.class)
    public void testCreatebookAlreadyExists() throws Exception {
        Book book = new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1");
        Mockito.when(bookRepository.existsByTitle(Mockito.anyString())).thenReturn(true);

        bookService.createBook(book);
    }

    @Test
    public void testGetbookById() throws BookNotFoundException {
        Book book = new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1");
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        Assert.assertEquals("Book Title1", result.getTitle());
        Assert.assertEquals(2023, result.getEditionYear());
    }

    @Test(expected = BookNotFoundException.class)
    public void testGetbookByIdNotFound() throws BookNotFoundException {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        bookService.getBookById(1L);
    }

    @Test
    public void testUpdatebook() throws BookNotFoundException {
        Book existingBook = new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1");
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(existingBook);

        Book updatedBook = new Book(1L,"Book Title1 Updated", "Author Name1", 2023, "ISBN123456789", "publisher1");
        Book result = bookService.updateBook(1L, updatedBook);

        Assert.assertEquals("Book Title1 Updated", result.getTitle());
        Assert.assertEquals(2023, result.getEditionYear());
    }

    @Test(expected = BookNotFoundException.class)
    public void testUpdatebookNotFound() throws BookNotFoundException {
        Book updatedBook = new Book(1L,"Book Title1 Updated", "Author Name1", 2023, "ISBN123456789", "publisher1");
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        bookService.updateBook(1L, updatedBook);
    }

    @Test
    public void testDeletebook() {
        bookService.deleteBook(1L);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(1L);
    }
}
