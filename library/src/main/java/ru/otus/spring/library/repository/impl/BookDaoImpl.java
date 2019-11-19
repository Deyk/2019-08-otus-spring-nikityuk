package ru.otus.spring.library.repository.impl;

import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDaoCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(readOnly = true)
public class BookDaoImpl implements BookDaoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> getAllWhereAuthorId(long authorId) {
        val books = em.createQuery("select b from Book b", Book.class).getResultList();
        return books.stream()
                .filter(book ->
                        book.getAuthors().stream()
                                .map(Author::getId).collect(Collectors.toList())
                                .contains(authorId))
                .collect(Collectors.toList());
    }
}
