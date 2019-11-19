package ru.otus.spring.library.repository.impl;

import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.domain.Comment;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.CommentDao;
import ru.otus.spring.library.repository.JpaRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class BookDaoJpa implements BookDao {
    private final AuthorDao authorDao;
    private final CommentDao commentDao;

    @PersistenceContext
    private EntityManager em;

    public BookDaoJpa(AuthorDao authorDao, CommentDao commentDao) {
        this.authorDao = authorDao;
        this.commentDao = commentDao;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveBook(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public Book getBookById(long id) throws JpaRepositoryException {
        return Optional.ofNullable(em.find(Book.class, id))
                .orElseThrow(() -> new JpaRepositoryException("Returned book is null"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void deleteBookById(long id) throws JpaRepositoryException {
        Book book = this.getBookById(id);
        for (Author author : book.getAuthors()) {
            authorDao.deleteById(author.getId());
        }
        List<Comment> comments = commentDao.getAllCommentsForBook(id);
        for (Comment comment : comments) {
            commentDao.deleteById(comment.getId());
        }
        em.remove(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public List<Book> getAllBooksWhereAuthorId(long authorId) {
        val books = em.createQuery("select b from Book b", Book.class).getResultList();
        return books.stream()
                .filter(book ->
                        book.getAuthors().stream()
                                .map(Author::getId).collect(Collectors.toList())
                                .contains(authorId))
                .collect(Collectors.toList());
    }
}
