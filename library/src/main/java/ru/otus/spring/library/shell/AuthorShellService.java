package ru.otus.spring.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

@ShellComponent
public class AuthorShellService {
    private final AuthorService authorService;
    private final MessageService ms;

    public AuthorShellService(AuthorService authorService, MessageService ms) {
        this.authorService = authorService;
        this.ms = ms;
    }

    @ShellMethod(value = "Add new author", key = {"aa", "addAuthor"})
    public void addAuthor(String name) {
        Author author = authorService.insertAuthor(name);
        ms.printMessage(author + " is created");
    }

    @ShellMethod(value = "Update existing author", key = {"ua", "updateAuthor"})
    public void updateAuthor(long id, String name) {
        try {
            ms.printMessage(authorService.updateAuthor(id, name) + " row is updated");
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        } catch (Exception ge) {
            ms.printMessage(ge.getMessage() + " Check input parameters!");
        }
    }

    @ShellMethod(value = "Get Author by id", key = {"ga", "getAuthor"})
    public void getAuthorById(long id) {
        try {
            Author author = (authorService.getAuthorById(id));
            ms.printMessage("Returned Author: " + author);
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        } catch (Exception ge) {
            ms.printMessage(ge.getMessage() + " Check input parameters!");
        }
    }

    @ShellMethod(value = "Delete Author by id", key = {"da", "deleteAuthor"})
    public void deleteAuthorById(long id) {
        try {
            ms.printMessage(authorService.deleteAuthorById(id) + " row is updated");
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        } catch (Exception ge) {
            ms.printMessage(ge.getMessage() + " Check input parameters!");
        }
    }

    @ShellMethod(value = "Get all Authors", key = {"gaa", "getAllAuthors"})
    public void getAllAuthors() {
        ms.printMessage("All authors: " + authorService.getAllAuthors());
    }
}
