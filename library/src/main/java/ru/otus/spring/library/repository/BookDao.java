package ru.otus.spring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.library.domain.Book;

public interface BookDao extends JpaRepository<Book, Long> {
}
