package ru.otus.spring.library.repository.impl;

import lombok.val;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.domain.Comment;
import ru.otus.spring.library.repository.CommentDaoCustom;
import ru.otus.spring.library.repository.JpaRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class CommentDaoImpl implements CommentDaoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment getCommentByIdWithBook(long commentId) throws JpaRepositoryException {
        val query = em.createQuery("select c from Comment c where c.id = :commentId", Comment.class);
        query.setParameter("commentId", commentId);
        try {
            Comment comment = query.getSingleResult();
            Hibernate.initialize(comment.getBook());
            return comment;
        } catch (Exception e) {
            throw new JpaRepositoryException("Returned comment is null");
        }
    }
}