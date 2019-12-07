package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(String text, String bookId) throws LibraryServiceException;

    Comment updateComment(String commentId, String text) throws LibraryServiceException;

    Comment getCommentById(String commentId) throws LibraryServiceException;

    void deleteCommentById(String commentId);

    List<Comment> getAllCommentsForBook(String bookId) throws LibraryServiceException;
}
