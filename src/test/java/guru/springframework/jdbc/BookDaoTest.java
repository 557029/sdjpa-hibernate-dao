package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoTest {
    @Autowired
    BookDao bookDao;

    @Test
    public void testBookGetId() {
        final Book book = this.bookDao.getById(1L);
        assert book != null;
    }

    @Test
    public void testBookGetByTitle() {
        final Book book = this.bookDao.getByTitle("Clean Code");
        assert  book != null;
    }

    @Test
    public void testSaveUpdateAndDelete(){
        // Create
        final Book book = new Book();
        book.setAuthorId(1L);
        book.setIsbn("978-0134494167");
        book.setPublisher("Test");
        book.setTitle("Test");
        final Book savedBook = this.bookDao.saveNewBook(book);
        assert this.bookDao.getById(savedBook.getId()) != null;
        savedBook.setTitle("Test1");
        Book updatedBook = this.bookDao.updateBook(savedBook);
        assert updatedBook != null;
        assert updatedBook.getTitle().equals("Test1");

        this.bookDao.deleteBookById(savedBook.getId());
        assert this.bookDao.getById(savedBook.getId()) == null;

    }
}
