package ru.otus.spring.library.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.util.StringUtils;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.domain.User;
import ru.otus.spring.library.service.*;

import java.util.List;

@ShellComponent
public class LibraryCommands {
    private final UserService userService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;
    private final MessageService messageService;
    private User user;

    public LibraryCommands(UserService userService, AuthorService authorService, BookService bookService, GenreService genreService, MessageService messageService) {
        this.userService = userService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
        this.messageService = messageService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login(String login, String password) {
        user = userService.login(login, password);
        messageService.printMessage("Hello " + user.getLogin() + " ! Enter 'help' to see available command...");
    }


    @ShellMethod(value = "Add new book", key = {"ab", "addBook"})
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public void addBook(String title) {
        Book book = bookService.addBook(title);
    }

    @ShellMethod(value = "Get book by id", key = {"gb", "getBook"})
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public void getBookById(long id) {
        Book book = bookService.getBookById(id);
    }

    @ShellMethod(value = "Find books by author", key = {"fba", "findBookByAuthor"})
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public void findBooksByAuthor(String name, String surname) {
        List<Book> books = bookService.findBooksByAuthor(name, surname);
    }

    @ShellMethod(value = "Find books by genre", key = {"fbg", "findBookByGenre"})
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public void findBooksByGenre(String genreTitle) {
        List<Book> books = bookService.findBooksByGenre(genreTitle);
    }

    @ShellMethod(value = "Delete book by id", key = {"db", "deleteBook"})
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public void deleteBookById(long id) {
        Book book = bookService.deleteBookById(id);
    }

    @ShellMethod(value = "Get all books", key = {"gab", "getAllBooks"})
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public void getAllBooks() {
        List<Book> books = bookService.getAllBooks();
    }

    private Availability isUserLoggedIn() {
        return this.user == null || StringUtils.isEmpty(this.user.getLogin()) || StringUtils.isEmpty(this.user.getPassword())
                ? Availability.unavailable("Please, login first")
                : Availability.available();
    }
}
