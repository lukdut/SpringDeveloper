package gerasimov.springdev.nosqllibrary;

import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class MongoLibraryFacadeTest {
    @Autowired
    LibraryFacade libraryFacade;

    @Test
    public void test() {
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
