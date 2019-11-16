package ru.otus.spring.library.repository.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.domain.Comment;
import ru.otus.spring.library.repository.CommentDao;
import ru.otus.spring.library.repository.JpaRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveComment(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    @Override
    public Comment getCommentById(long commentId) throws JpaRepositoryException {
        return Optional.ofNullable(em.find(Comment.class, commentId))
                .orElseThrow(() -> new JpaRepositoryException("Returned comment is null"));
    }

    @Override
    public Comment getCommentByIdWithBook(long commentId) throws JpaRepositoryException {
        Comment comment = this.getCommentById(commentId);
        Hibernate.initialize(comment.getBook());
        return comment;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void deleteCommentById(long commentId) throws JpaRepositoryException {
        em.remove(this.getCommentById(commentId));
    }

    @Override
    public List<Comment> getAllCommentsForBook(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}