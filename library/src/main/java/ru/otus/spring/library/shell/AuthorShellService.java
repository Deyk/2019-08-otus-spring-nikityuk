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
        try {
            ms.printMessage("Author added: " + authorService.addAuthor(name));
        } catch (Exception e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Update existing author", key = {"ua", "updateAuthor"})
    public void updateAuthor(String id, String name) {
        try {
            ms.printMessage("Author updated: " + authorService.updateAuthor(id, name));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Get Author by id", key = {"ga", "getAuthor"})
    public void getAuthorById(String id) {
        try {
            ms.printMessage("Returned author: " + authorService.getAuthorById(id));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete Author by id", key = {"da", "deleteAuthor"})
    public void deleteAuthorById(String id) {
        try {
            authorService.deleteAuthorById(id);
            ms.printMessage("Author " + id + " is deleted");
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Get all Authors", key = {"gaa", "getAllAuthors"})
    public void getAllAuthors() {
        ms.printMessage("All authors: " + authorService.getAllAuthors());
    }
}
