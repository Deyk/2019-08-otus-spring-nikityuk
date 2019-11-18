package ru.otus.spring.library.repository.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.domain.Comment;
import ru.otus.spring.library.repository.CommentDao;
import ru.otus.spring.library.repository.CommentDaoCustom;
import ru.otus.spring.library.repository.JpaRepositoryException;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class CommentDaoImpl implements CommentDaoCustom {
    @Autowired
    CommentDao commentDao;

    @Override
    public Comment getCommentByIdWithBook(long commentId) throws JpaRepositoryException {
        Optional<Comment> commentOptional = commentDao.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            Hibernate.initialize(comment.getBook());
            return comment;
        } else {
            throw new JpaRepositoryException("Returned comment is null");
        }
    }
}