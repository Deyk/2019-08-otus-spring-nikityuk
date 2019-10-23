package ru.otus.spring.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.library.domain.Book;
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

    @ShellMethod(value = "Add new book with existing author", key = {"ab", "addBook"})
    public void addBook(String title, long authorId) {
        Book book = bookService.addBook(title, authorId);
    }

    @ShellMethod(value = "Update existing book", key = {"ub", "updateBook"})
    public void updateBook(long id, String title, long authorId) {
        try {
            ms.printMessage(bookService.updateBook(id, title, authorId) + " row is updated");
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        } catch (Exception ge) {
            ms.printMessage(ge.getMessage() + " Check input parameters!");
        }
    }

    @ShellMethod(value = "Get book by id", key = {"gb", "getBook"})
    public void getBookById(long id) {
        try {
            Book book = (bookService.getBookById(id));
            ms.printMessage("Returned Book: " + book);
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        } catch (Exception ge) {
            ms.printMessage(ge.getMessage() + " Check input parameters!");
        }
    }

    @ShellMethod(value = "Delete book by id", key = {"db", "deleteBook"})
    public void deleteBookById(long id) {
        ms.printMessage(bookService.deleteBookById(id) + " row is updated");
    }

    @ShellMethod(value = "Get all books", key = {"gab", "getAllBooks"})
    public void getAllBooks() {
        ms.printMessage("All books: " + bookService.getAllBooks());
    }
}
