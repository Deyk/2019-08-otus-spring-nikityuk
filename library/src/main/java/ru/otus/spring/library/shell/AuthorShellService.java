package ru.otus.spring.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
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
            ms.printMessage(authorService.addAuthor(name).toString());
        } catch (Exception e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Update existing author or create new one", key = {"ua", "updateAuthor"})
    public void updateAuthor(String oldName, String name) {
        try {
            ms.printMessage(authorService.updateAuthor(oldName, name).toString());
        } catch (Exception e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Get Author by id", key = {"ga", "getAuthor"})
    public void getAuthorById(long id) {
        try {
            ms.printMessage("Returned Author: " + authorService.getAuthorById(id));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete Author by id", key = {"da", "deleteAuthor"})
    public void deleteAuthorById(long id) {
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
