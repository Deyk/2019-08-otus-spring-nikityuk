package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookDaoCustom {
    List<Book> getAllWhereAuthorId(long authorId);
}
