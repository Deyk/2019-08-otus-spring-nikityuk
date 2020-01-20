package ru.otus.spring.library.rest;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.rest.model.BookDto;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String showBooks(Model model) {
        List<Book> allBooks = bookService.getAllBooks();
        model.addAttribute("books", allBooks.stream().map(BookDto::toDto).collect(Collectors.toList()));
        return "bookList";
    }

    @PostMapping("/books/add")
    public String addBook(
            @RequestParam("title") String name,
            @RequestParam("authorName") String authorName) {
        bookService.addBook(name, authorName);
        return "redirect:/books";
    }

    @GetMapping("/books/edit")
    public String editPage(
            @RequestParam("id") String id,
            Model model) throws LibraryServiceException {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "bookEdit";
    }

    @PostMapping("/books/edit")
    public String editBooks(BookRequest request) throws LibraryServiceException {
        bookService.updateBook(request.getId(), request.getTitle(), request.getAuthorNames().get(request.getAuthorIndex()));
        return "redirect:/books";
    }

    @DeleteMapping("/books/delete")
    public String deleteBook(@RequestParam("id") String id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    @ExceptionHandler(LibraryServiceException.class)
    public ResponseEntity<String> handleNotEnoughFunds(LibraryServiceException ex) {
        return ResponseEntity.badRequest().body("Not found book");
    }

    @Data
    class BookRequest {
        private String id;
        private String title;
        private List<String> authorNames;
        private int authorIndex;
    }
}
