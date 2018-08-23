package gerasimov.springdev.library;

import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.repository.AuthorsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorsRepositoryTest {

    @Autowired
    AuthorsRepository authorsRepository;

    @Test
    public void test() {
        int sizeBefore = authorsRepository.findAll().size();
        authorsRepository.save(new Author("name1"));
        authorsRepository.save(new Author("name2"));
        Assert.assertEquals(2, authorsRepository.findAll().size() - sizeBefore);
        Assert.assertTrue(authorsRepository.findAuthorByFullName("name1").isPresent());
        Assert.assertEquals("name1", authorsRepository.findAuthorByFullName("name1").get().getFullName());
        Assert.assertFalse(authorsRepository.findAuthorByFullName("name3").isPresent());
    }
}
