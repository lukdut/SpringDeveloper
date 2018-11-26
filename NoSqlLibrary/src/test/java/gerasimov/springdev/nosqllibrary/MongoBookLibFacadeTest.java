package gerasimov.springdev.nosqllibrary;

import gerasimov.springdev.nosqllibrary.facade.MongoBookLibFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoBookLibFacadeTest {
    @Qualifier("mongoBookLibFacade")
    @Autowired
    MongoBookLibFacade bookLibFacade;
    private Book authorsBook = new Book("authors");

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void adminCanDelete() {
        String id = bookLibFacade.addBook(new Book("test"));
        Assert.assertNotNull(id);
        bookLibFacade.deleteBook(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unauthorized() {
        String id = bookLibFacade.addBook(new Book("test"));
        Assert.assertNotNull(id);
        bookLibFacade.deleteBook(id);
    }
}
