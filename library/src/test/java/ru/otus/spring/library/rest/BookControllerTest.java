package ru.otus.spring.library.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.library.service.BookService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void showBooks() throws Exception {
        mockMvc.perform(get("/books")).andExpect(matchAll(
                status().isOk(),
                model().size(1),
                model().attributeExists("books"),
                view().name("bookList")));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void addBook() throws Exception {
        mockMvc.perform(post("/books/add")
                .param("title", "testTitle")
                .param("authorName", "testAuthor")
        ).andExpect(matchAll(
                status().isFound(),
                redirectedUrl("/books")));

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