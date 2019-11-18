package ru.otus.spring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.library.domain.Comment;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long>, CommentDaoCustom {

    @Query("select c from Comment c where c.book.id = :bookId")
    List<Comment> getAllCommentsForBook(@Param("bookId") long bookId);
}
