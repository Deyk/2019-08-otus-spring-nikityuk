package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Comment;

import java.util.List;

public interface CommentDao {

    void saveComment(Comment comment);

    Comment getCommentById(long commentId) throws JpaRepositoryException;

    Comment getCommentByIdWithBook(long commentId) throws JpaRepositoryException;

    void deleteCommentById(long commentId) throws JpaRepositoryException;

    List<Comment> getAllCommentsForBook(long bookId);
}
