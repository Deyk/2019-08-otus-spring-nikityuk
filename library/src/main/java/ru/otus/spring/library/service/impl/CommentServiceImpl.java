package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.domain.Comment;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.CommentDao;
import ru.otus.spring.library.service.CommentService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookDao bookDao;

    public CommentServiceImpl(CommentDao commentDao, BookDao bookDao) {
        this.commentDao = commentDao;
        this.bookDao = bookDao;
    }

    @Override
    public Comment addComment(String text, String bookId) throws LibraryServiceException {
        Book book = bookDao.findById(bookId).orElseThrow(() -> new LibraryServiceException("Can't add comment to book with id: " + bookId));
        Comment comment = new Comment(text, new Date(), book);
        commentDao.save(comment);
        return comment;
    }

    @Override
    public Comment updateComment(String commentId, String text, String bookId) throws LibraryServiceException {
        Comment comment;
        comment = commentDao.findById(commentId).orElseThrow(() -> new LibraryServiceException("Can't get comment with id: " + commentId));
        Book book = bookDao.findById(bookId).orElseThrow(() -> new LibraryServiceException("Can't add comment to book with id: " + bookId));
        comment.setText(text);
        comment.setBook(book);
        commentDao.save(comment);
        return comment;
    }

    @Override
    public Comment getCommentById(String commentId) throws LibraryServiceException {
        return commentDao.findById(commentId).orElseThrow(() -> new LibraryServiceException("Can't get comment with id: " + commentId));
    }

    @Override
    public Comment getCommentByIdWithBook(String commentId) throws LibraryServiceException {
        return commentDao.findById(commentId).orElseThrow(() -> new LibraryServiceException("Can't get comment with id: " + commentId));
    }

    @Override
    public void deleteCommentById(String commentId) {
        commentDao.deleteById(commentId);
    }

    @Override
    public List<Comment> getAllCommentsForBook(String bookId) {
        return (List<Comment>) commentDao.findAllById(Collections.singleton(bookId));
    }
}
