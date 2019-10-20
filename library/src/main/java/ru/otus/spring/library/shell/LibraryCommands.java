package ru.otus.spring.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.MessageService;

import java.util.List;

@ShellComponent
public class LibraryCommands {
    private final BookService bookService;
    //    private final AuthorService authorService;
//    private final GenreService genreService;
    private final MessageService ms;

    public LibraryCommands(BookService bookService, MessageService ms) {
        this.bookService = bookService;
        this.ms = ms;
    }

    @ShellMethod(value = "Add new book", key = {"ab", "addBook"})
    public void addBook(String title, long authorId) {
        Book book = bookService.addBook(title, authorId);
    }

    @ShellMethod(value = "Get book by id", key = {"gb", "getBook"})
    public void getBookById(long id) {
        Book book = (bookService.getBookById(id));
        if (book != null) {
            ms.printMessage("Returned book: " + book);
        } else {
            ms.printMessage("Can't get book with id: " + id);
        }
    }

    @ShellMethod(value = "Delete book by id", key = {"db", "deleteBook"})
    public void deleteBookById(long id) {
        Book book = bookService.deleteBookById(id);
    }

    @ShellMethod(value = "Get all books", key = {"gab", "getAllBooks"})
    public void getAllBooks() {
        List<Book> books = bookService.getAllBooks();
    }
}
