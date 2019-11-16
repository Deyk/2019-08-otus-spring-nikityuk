package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(String text, long bookId) throws LibraryServiceException;

    Comment updateComment(long commentId, String text, long bookId) throws LibraryServiceException;

    Comment getCommentById(long commentId) throws LibraryServiceException;

    Comment getCommentByIdWithBook(long commentId) throws LibraryServiceException;

    void deleteCommentById(long commentId) throws LibraryServiceException;

    List<Comment> getAllCommentsForBook(long bookId);
}
