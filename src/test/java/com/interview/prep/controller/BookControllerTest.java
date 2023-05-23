package com.interview.prep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.prep.model.Book;
import com.interview.prep.service.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1");
        Book book2 = new Book(1L,"Book Title2", "Author Name2", 2021, "ISBN123456799", "publisher2");
        List<Book> books = Arrays.asList(book1, book2);
        when(bookService.getAllBooks()).thenReturn(books);
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(books)));
    }

    @Test
    public void testGetBookById() throws Exception {
        Long BookId = 1L;
        Book book = new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1");
        when(bookService.getBookById(BookId)).thenReturn(book);
        mockMvc.perform(get("/books/{id}", BookId))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(book)));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1");
        when(bookService.createBook(book)).thenReturn(book);
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBook() throws Exception {
        Long BookId = 1L;
        Book book = new Book(1L,"Book Title1", "Author Name1", 2023, "ISBN123456789", "publisher1");
        Book updatedBook = new Book(2L,"Book Title2", "Author Name2", 2021, "ISBN123456799", "publisher2");
        when(bookService.updateBook(BookId, updatedBook)).thenReturn(updatedBook);
        mockMvc.perform(put("/books/{id}", BookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBook)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long BookId = 1L;
        mockMvc.perform(delete("/books/{id}", BookId))
                .andExpect(status().isOk());
        verify(bookService, Mockito.times(1)).deleteBook(BookId);
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



