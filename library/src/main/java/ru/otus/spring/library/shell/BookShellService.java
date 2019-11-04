package ru.otus.spring.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

@ShellComponent
public class BookShellService {
    private final BookService bookService;
    private final MessageService ms;

    public BookShellService(BookService bookService, MessageService ms) {
        this.bookService = bookService;
        this.ms = ms;
    }

    @ShellMethod(value = "Update existing book or create new one", key = {"ub", "updateBook"})
    public void updateBook(String title, String authorName) {
        ms.printMessage(bookService.updateBook(title, authorName).toString());
    }

    @ShellMethod(value = "Get book by id", key = {"gb", "getBook"})
    public void getBookById(long id) {
        try {
            ms.printMessage("Returned Book: " + bookService.getBookById(id));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete book by id", key = {"db", "deleteBook"})
    public void deleteBookById(long id) {
        try {
            bookService.deleteBookById(id);
            ms.printMessage("Book " + id + " is deleted");
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Get all books", key = {"gab", "getAllBooks"})
    public void getAllBooks() {
        ms.printMessage("All books: " + bookService.getAllBooks());
    }
}
