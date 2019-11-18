package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Comment;

public interface CommentDaoCustom {

    Comment getCommentByIdWithBook(long commentId) throws JpaRepositoryException;
}
