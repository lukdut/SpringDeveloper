package gerasimov.springdev.nosqllibrary;

import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataMongoTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    public void contextLoads() {
        List<Book> in = bookRepository.findByTitleLike("in");
        Assert.assertEquals(0, in.size());
        Assert.assertFalse(bookRepository.findByTitleIgnoreCase("title").isPresent());
        bookRepository.insert(new Book("title"));
        Assert.assertTrue(bookRepository.findByTitleIgnoreCase("title").isPresent());
    }

}
