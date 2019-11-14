package ru.otus.spring.library.repository.impl;

import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.JpaRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveAuthor(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
        } else {
            em.merge(author);
        }
    }

    @Override
    public Author getAuthorById(long id) throws JpaRepositoryException {
        return Optional.ofNullable(em.find(Author.class, id))
                .orElseThrow(() -> new JpaRepositoryException("Returned author is null"));
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        val query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        List<Author> authors = query.getResultList();
        if (authors.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(authors.get(0));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void deleteAuthorById(long id) throws JpaRepositoryException {
        Author author = this.getAuthorById(id);
        val query = em.createNativeQuery("delete from books_authors where author_id = :authorId");
        query.setParameter("authorId", id);
        query.executeUpdate();
        em.remove(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }
}
