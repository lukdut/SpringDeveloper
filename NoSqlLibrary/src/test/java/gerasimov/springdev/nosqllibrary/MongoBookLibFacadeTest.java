package gerasimov.springdev.nosqllibrary;

import gerasimov.springdev.nosqllibrary.facade.MongoBookLibFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MongoBookLibFacadeTest {
    @Autowired
    MongoBookLibFacade bookLibFacade;
    private Book authorsBook = new Book("authors");

    @Before
    @WithMockUser(username = "author")
    public void prepare() {
        bookLibFacade.addBook(authorsBook);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void adminCanCreate() {
        String id = bookLibFacade.addBook(new Book("test"));
        Assert.assertNotNull(id);
        bookLibFacade.deleteBook(id);
    }

    @Test
    @WithMockUser(username = "user")
    public void userCanNonUpdate() {
        authorsBook.setTitle("new");
        bookLibFacade.updateBook(authorsBook);
    }
}
