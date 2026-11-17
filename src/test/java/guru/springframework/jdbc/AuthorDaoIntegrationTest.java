package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by jt on 8/20/21.
 */
@DataJpaTest
@Import(AuthorDaoImpl.class)
public abstract class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();

    }
}

// added to enable the test-configured datasource to be used with P6Spy for SQL logs with bound values.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class H2Test extends AuthorDaoIntegrationTest {
}

@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySQLIntegrationTest extends AuthorDaoIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isGreaterThan(0);
    }

}