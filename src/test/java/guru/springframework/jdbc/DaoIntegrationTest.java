package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by jt on 8/28/21.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {
    @Autowired
    AuthorDao authorDao;

    @Test
    public void testGetAuthorById() {
        Author a = authorDao.getById(1L);
        assert a != null;
    }

    @Test
    public void testGetAuthorByName() {
        Author author = this.authorDao.findAuthorByName("Craig","Walls");
        assert author != null;
    }

    @Test
    public void testSaveAuthor() {
        final Author author = new Author();
        author.setFirstName("Igor");
        author.setLastName("Aleshin");
        final Author savedAuthor = this.authorDao.saveNewAuthor(author);
        assert savedAuthor.getId() != null;

        savedAuthor.setFirstName("Igor1");
        this.authorDao.updateAuthor(savedAuthor);
        assert savedAuthor.getId() != null;

        this.authorDao.deleteAuthorById(savedAuthor.getId());
        Author deletedAuthor = this.authorDao.getById(savedAuthor.getId());
        assert deletedAuthor == null;
    }
}
