package ru.otus.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.domain.Comment;

public interface CommentDao extends MongoRepository<Comment, String> {


}
