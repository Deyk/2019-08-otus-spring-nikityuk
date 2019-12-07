package ru.otus.spring.library.event;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.service.MessageService;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class BookCascadeMongoEventListener extends AbstractMongoEventListener<Book> {
    private static final String ID = "_id";
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private MessageService ms;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        final Book book = event.getSource();
        if (book.getAuthors() != null) {
            book.getAuthors().forEach(author -> {
                if (!mongoOperations.exists(query(where("name").is(author.getName())), Author.class)) {
                    mongoOperations.save(author);
                    ms.printMessage("{Book Event} Author added: " + author);
                } else {
                    Author existingAuthor = mongoOperations.findOne(query(where("name").is(author.getName())), Author.class);
                    author.setId(Objects.requireNonNull(existingAuthor).getId());
                    ms.printMessage("{Book Event} AuthorId changed to " + author.getId());
                }
            });
        }
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        final Document document = event.getDocument();
        if (document != null) {
            String bookId = document.get(ID).toString();
            Book book = mongoOperations.findById(bookId, Book.class);
            if (book != null) {
                book.getComments().forEach(comment -> {
                    mongoOperations.remove(comment);
                    ms.printMessage("{Book Event} Comment " + comment.getId() + " is deleted from book");
                });
            }
        }
    }
}
