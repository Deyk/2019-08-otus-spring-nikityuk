//package ru.otus.spring.library.repository.impl;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.transaction.annotation.Transactional;
//import ru.otus.spring.library.domain.Book;
//import ru.otus.spring.library.domain.Comment;
//import ru.otus.spring.library.repository.CommentDao;
//import ru.otus.spring.library.repository.JpaRepositoryException;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DisplayName("Тесты репозитория для работы с комментариями")
//@DataJpaTest
//class CommentDaoImplTest {
//    private static final long EXISTING_COMMENT_ID = 1L;
//    private static final String EXISTING_COMMENT_TEXT = "comment_01";
//    private static final long EXISTING_COMMENT_DATE_TIME = 1573318800000L;
//    private static final long EXISTING_BOOK_ID = 1L;
//    private static final String ID = "id";
//    private static final String TEXT = "text";
//
//    @Autowired
//    CommentDao commentDao;
//
//    @Autowired
//    private TestEntityManager tem;
//
//    @Test
//    @DisplayName("Должен получать существующий комментарий")
//    void getCommentById() {
//        Optional<Comment> comment = commentDao.findById(EXISTING_COMMENT_ID);
//        comment.ifPresent(value -> {
//                    assertEquals(value.getId(), EXISTING_COMMENT_ID);
//                    assertEquals(value.getText(), EXISTING_COMMENT_TEXT);
//                    assertEquals(value.getDate().getTime(), EXISTING_COMMENT_DATE_TIME);
//                }
//        );
//    }
//
//    @Test
//    @DisplayName("Должен получать существующий комментарий вместе с книгой")
//    void getCommentByIdWithBook() throws JpaRepositoryException {
//        Comment comment = commentDao.getCommentByIdWithBook(EXISTING_COMMENT_ID);
//        assertThat(comment)
//                .hasFieldOrPropertyWithValue(ID, EXISTING_COMMENT_ID)
//                .hasFieldOrPropertyWithValue(TEXT, EXISTING_COMMENT_TEXT);
//        assertEquals(comment.getDate().getTime(), EXISTING_COMMENT_DATE_TIME);
//
//        Book book = comment.getBook();
//        Book expectedBook = tem.find(Book.class, EXISTING_BOOK_ID);
//        assertEquals(book.getId(), expectedBook.getId());
//        assertEquals(book.getTitle(), expectedBook.getTitle());
//
//        assertThat(book.getAuthors()).hasSize(2).contains(expectedBook.getAuthors().get(0), expectedBook.getAuthors().get(1));
//    }
//
//    @Test
//    @DisplayName("Должен получать все комментарии для книги по ее id")
//    void getAllWhereBookId() {
//        List<Comment> comments = commentDao.getAllByBook_Id(EXISTING_BOOK_ID);
//        assertEquals(comments.size(), 2);
//    }
//
//    @Test
//    @DisplayName("Должен удалять все комментарии для книги по ее id")
//    void deleteAllWhereBookId() {
//        commentDao.deleteAllByBook_Id(EXISTING_BOOK_ID);
//        assertThat(commentDao.findAll()).isEmpty();
//    }
//}