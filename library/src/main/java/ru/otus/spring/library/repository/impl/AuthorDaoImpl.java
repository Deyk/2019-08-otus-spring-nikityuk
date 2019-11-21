package ru.otus.spring.library.repository.impl;

import lombok.val;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDaoCustom;
import ru.otus.spring.library.repository.JpaRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class AuthorDaoImpl implements AuthorDaoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author getAuthorByIdWithBook(long id) throws JpaRepositoryException {
        val query = em.createQuery("select a from Author a where a.id = :id", Author.class);
        query.setParameter("id", id);
        try {
            Author author = query.getSingleResult();
            Hibernate.initialize(author.getBooks());
            return author;
        } catch (Exception e) {
            throw new JpaRepositoryException("Returned comment is null");
        }
    }

    @Override
    public Optional<Author> findByNameWithBook(String name) {
        val query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        try {
            Optional<Author> authorOptional = Optional.ofNullable(query.getSingleResult());
            authorOptional.ifPresent(author -> Hibernate.initialize(author.getBooks()));
            return authorOptional;
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
