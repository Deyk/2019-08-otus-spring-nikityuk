package ru.otus.spring.library.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.service.MessageService;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class AuthorCascadeMongoEventListener extends AbstractMongoEventListener<Author> {
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private MessageService ms;

    @Override
    public void onAfterSave(AfterSaveEvent<Author> event) {
        final Author author = event.getSource();
        List<Book> books = mongoOperations.find(query(where("authors.id").is(author.getId())), Book.class);
        books.forEach(book -> {
            book.getAuthors().stream()
                    .filter(a -> author.getId().equals(a.getId()))
                    .findFirst().ifPresent(authorToUpdate -> authorToUpdate.setName(author.getName()));
            mongoOperations.save(book);
            ms.printMessage("{Author Event} Book updated: " + author + " is changed");
        });
    }
}
