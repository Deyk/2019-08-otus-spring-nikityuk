package ru.otus.spring.library.repository.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JpaRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(value = Transactional.TxType.REQUIRES_NEW)
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book saveBook(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Book getBookById(long id) throws JpaRepositoryException {
        return Optional.ofNullable(em.find(Book.class, id))
                .orElseThrow(() -> new JpaRepositoryException("Returned book is null"));
    }

    @Override
    public void deleteBookById(long id) throws JpaRepositoryException {
        em.remove(this.getBookById(id));
    }

    @Override
    public List<Book> getAllBooks() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }
}
