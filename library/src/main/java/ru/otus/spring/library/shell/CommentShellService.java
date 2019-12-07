package ru.otus.spring.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.library.service.CommentService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

@ShellComponent
public class CommentShellService {
    private final CommentService commentService;
    private final MessageService ms;

    public CommentShellService(CommentService commentService, MessageService ms) {
        this.commentService = commentService;
        this.ms = ms;
    }

    @ShellMethod(value = "Add new comment to the book", key = {"ac", "addComment"})
    public void addComment(String text, String bookId) {
        try {
            ms.printMessage("Comment added: " + commentService.addComment(text, bookId));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Add new comment to the book", key = {"uc", "updateComment"})
    public void updateComment(String commentId, String text) {
        try {
            ms.printMessage("Comment updated: " + commentService.updateComment(commentId, text));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Get comment to the book", key = {"gc", "getComment"})
    public void getCommentById(String commentId) {
        try {
            ms.printMessage("Comment lazy loaded: " + commentService.getCommentById(commentId));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete comment to the book", key = {"dc", "deleteComment"})
    public void deleteCommentById(String commentId) {
        commentService.deleteCommentById(commentId);
        ms.printMessage("Comment " + commentId + " is deleted");
    }

    @ShellMethod(value = "Get all comments for book", key = {"gacfb", "getAllCommentsForBook"})
    public void getAllCommentsForBook(String bookId) {
        try {
            commentService.getAllCommentsForBook(bookId).forEach(comment ->
                    ms.printMessage("Comment lazy loaded: " + comment));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }
}
