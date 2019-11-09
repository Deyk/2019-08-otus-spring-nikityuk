package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.domain.Comment;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.CommentDao;
import ru.otus.spring.library.repository.JpaRepositoryException;
import ru.otus.spring.library.service.CommentService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookDao bookDao;
    private final MessageService ms;

    public CommentServiceImpl(CommentDao commentDao, BookDao bookDao, MessageService ms) {
        this.commentDao = commentDao;
        this.bookDao = bookDao;
        this.ms = ms;
    }

    @Override
    public Comment addComment(String text, long bookId) throws LibraryServiceException {
        try {
            return commentDao.saveComment(new Comment(0, text, new Date(), bookDao.getBookById(bookId)));
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't add comment to book with id: " + bookId);
        }
    }

    @Override
    public Comment updateComment(long commentId, String text, long bookId) throws LibraryServiceException {
        Comment comment;
        Book book;

        try {
            comment = commentDao.getCommentByIdWithBook(commentId);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get comment with id: " + commentId);
        }

        try {
            book = bookDao.getBookById(bookId);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get book with id: " + bookId);
        }

        comment.setText(text);
        comment.setBook(book);
        return commentDao.saveComment(comment);
    }

    @Override
    public Comment getCommentById(long commentId) throws LibraryServiceException {
        try {
            return commentDao.getCommentById(commentId);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get comment with id: " + commentId);
        }
    }

    @Override
    public Comment getCommentByIdWithBook(long commentId) throws LibraryServiceException {
        try {
            return commentDao.getCommentByIdWithBook(commentId);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get comment with id: " + commentId);
        }
    }

    @Override
    public void deleteCommentById(long commentId) throws LibraryServiceException {
        try {
            commentDao.deleteCommentById(commentId);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't delete comment with id: " + commentId);
        }
    }

    @Override
    public List<Comment> getAllCommentsForBook(long bookId) {
        return commentDao.getAllCommentsForBook(bookId);
    }
}
