package ru.otus.spring.library.rest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.rest.model.BookDto;
import ru.otus.spring.library.service.BookService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @Mock
    private BookController bookController;

    @Test
    void getAllBooks() throws Exception {
        mockMvc.perform(get("/books")).andExpect(matchAll(
                status().isOk(),
                result -> assertEquals(APPLICATION_JSON_CHARSET_UTF_8, result.getResponse().getContentType())));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void addBook() throws Exception {
        when(bookService.addBook(anyString(), anyString())).thenReturn(new Book());
        when(bookController.getBookDto(any())).thenReturn(new BookDto());

        mockMvc.perform(post("/books/add")
                .param("title", "testTitle")
                .param("authorName", "testAuthor")
        ).andExpect(matchAll(
                status().isOk(),
                result -> {
                    assertEquals(APPLICATION_JSON_CHARSET_UTF_8, result.getResponse().getContentType());
                    assertEquals("{\"id\":null,\"title\":null,\"authors\":null,\"selectedAuthor\":null}", result.getResponse().getContentAsString());
                }));

        verify(bookService, times(1)).addBook(anyString(), anyString());
    }

    @Test
    void addBook_bad_param() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/books/add")
                .param("title", "testTitle")
                .param("badParam", "testAuthor")
        ).andExpect(matchAll(
                status().isBadRequest())
        ).andReturn();

        assertEquals(mvcResult.getResolvedException().getMessage(), "Required String parameter 'authorName' is not present");
    }
}