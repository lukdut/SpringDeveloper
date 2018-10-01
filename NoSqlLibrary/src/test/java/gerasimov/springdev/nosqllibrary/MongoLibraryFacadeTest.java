package gerasimov.springdev.nosqllibrary;

import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import gerasimov.springdev.nosqllibrary.facade.MongoLibraryFacade;
import gerasimov.springdev.nosqllibrary.repository.AuthorRepository;
import gerasimov.springdev.nosqllibrary.repository.BookRepository;
import gerasimov.springdev.nosqllibrary.repository.GenresRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest
/*  При попытке заавтовайрить LibraryFacade тест "зависал" гдето на создании контекста
    и до самого метода test выполнение не доходило. Поэтому добавил аннотацию DataMongoTest,
    дабы создались только репозитории и из них сконстрактил LibraryFacade
 */
@DataMongoTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class MongoLibraryFacadeTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenresRepository genresRepository;

    @Test
    public void test() {
        LibraryFacade libraryFacade = new MongoLibraryFacade(bookRepository, authorRepository, genresRepository, mongoTemplate);
        Assert.assertTrue(libraryFacade.findBook("none").length() < 5);
        libraryFacade.addBook("book", Collections.singletonList("author"), Collections.singletonList("genre"));
        String bookSummary = libraryFacade.findBook("book");
        Assert.assertTrue(bookSummary.contains("author"));
        String bookId = bookSummary.substring(bookSummary.lastIndexOf(" ") + 1);
        //Assert.assertTrue(libraryFacade.showBookInfo(bookId).get().getGenresIds().contains("genre"));
        Assert.assertFalse(libraryFacade.showBookInfo(bookId).get().getComments().contains("commentaryTest"));
        libraryFacade.commentBook(bookId, "commentaryTest");
        Assert.assertTrue(libraryFacade.showBookInfo(bookId).get().getComments().contains("commentaryTest"));
    }
}
