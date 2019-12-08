package ru.otus.spring.library.config.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.domain.Comment;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@ChangeLog
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "initData", author = "databaseChangelog")
    public void initData(MongoTemplate mongoTemplate) {
        Author author_1 = mongoTemplate.save(new Author("author_1"));
        Author author_2 = mongoTemplate.save(new Author("author_2"));
        Author author_3 = mongoTemplate.save(new Author("author_3"));

        Comment comment_1 = mongoTemplate.save(new Comment("comment_1", new Date()));
        Comment comment_2 = mongoTemplate.save(new Comment("comment_2", new Date()));
        Comment comment_3 = mongoTemplate.save(new Comment("comment_3", new Date()));

        mongoTemplate.save(new Book("book_1", Collections.singletonList(author_1), Collections.singletonList(comment_1)));
        mongoTemplate.save(new Book("book_2", Arrays.asList(author_1, author_2), Arrays.asList(comment_1, comment_2)));
        mongoTemplate.save(new Book("book_3", Arrays.asList(author_1, author_2, author_3), Arrays.asList(comment_1, comment_2, comment_3)));
    }

    @ChangeSet(order = "002", id = "initTestData", author = "databaseChangelog")
    @Profile("test")
    public void initTestData(MongoTemplate mongoTemplate) {
        Author author_1 = mongoTemplate.save(new Author("author_1"));
        Author author_2 = mongoTemplate.save(new Author("author_2"));

        Comment comment_1 = mongoTemplate.save(new Comment("comment_1", new Date()));
        Comment comment_2 = mongoTemplate.save(new Comment("comment_2", new Date()));

        mongoTemplate.save(new Book("book_1", Collections.singletonList(author_1), Collections.singletonList(comment_1)));
        mongoTemplate.save(new Book("book_2", Arrays.asList(author_1, author_2), Arrays.asList(comment_1, comment_2)));
    }
}
