package ru.otus.spring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends JpaRepository<Author, Long>, AuthorDaoCustom {
    Optional<Author> findByName(String name);
}
