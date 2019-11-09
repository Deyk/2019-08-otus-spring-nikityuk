package ru.otus.spring.library.repository.impl;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.JpaRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(value = Transactional.TxType.REQUIRES_NEW)
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author saveAuthor(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
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
    public void deleteAuthorById(long id) throws JpaRepositoryException {
        em.remove(this.getAuthorById(id));
    }

    @Override
    public List<Author> getAllAuthors() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }
}
