package ru.otus.spring.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.library.domain.Comment;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long>, CommentDaoCustom {

    List<Comment> getAllByBook_Id(@Param("bookId") long bookId);

    void deleteAllByBook_Id(@Param("bookId") long bookId);
}
