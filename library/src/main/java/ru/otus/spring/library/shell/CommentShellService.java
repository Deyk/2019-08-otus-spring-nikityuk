package ru.otus.spring.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.library.domain.Comment;
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
    public void addComment(String text, long bookId) {
        try {
            Comment comment = commentService.addComment(text, bookId);
            ms.printMessage("Comment added: " + comment.getId() + " text: " + comment.getText() + " date: " + comment.getDate());
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Add new comment to the book", key = {"uc", "updateComment"})
    public void updateComment(long commentId, String text, long bookId) {
        try {
            Comment comment = commentService.updateComment(commentId, text, bookId);
            ms.printMessage("Comment updated: " + comment.getId() + " text: " + comment.getText() + " date: " + comment.getDate());
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Get comment to the book", key = {"gc", "getComment"})
    public void getCommentById(long commentId) {
        try {
            Comment comment = commentService.getCommentById(commentId);
            ms.printMessage("Comment lazy loaded: " + comment.getId() + " text: " + comment.getText() + " date: " + comment.getDate());
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Get comment to the book with book", key = {"gcwb", "getCommentWithBook"})
    public void getCommentByIdWithBook(long commentId) {
        try {
            ms.printMessage(commentService.getCommentByIdWithBook(commentId).toString());
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete comment to the book", key = {"dc", "deleteComment"})
    public void deleteCommentById(long commentId) {
        commentService.deleteCommentById(commentId);
        ms.printMessage("Comment " + commentId + " is deleted");
    }

    @ShellMethod(value = "Get all comments for book", key = {"gacfb", "getAllCommentsForBook"})
    public void getAllCommentsForBook(long bookId) {
        commentService.getAllCommentsForBook(bookId).forEach(comment ->
                ms.printMessage("Comment lazy loaded: " + comment.getId() + " text: " + comment.getText() + " date: " + comment.getDate()));
    }
}
