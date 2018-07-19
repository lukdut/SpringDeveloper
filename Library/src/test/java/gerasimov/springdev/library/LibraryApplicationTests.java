package gerasimov.springdev.library;

import gerasimov.springdev.library.dao.BooksDAO;
import gerasimov.springdev.library.model.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryApplicationTests {

    @Autowired
    private BooksDAO booksDAO;


    @Autowired
    private DBUtils dbUtils;

    @Test
    public void noAuthorGenresDuplicationTest() {
        String title = "title";
        String author = "author";
        String authorNew = "authorNew";
        String genre = "genre";
        String genreNew = "genreNew";

        dbUtils.clearDB();

        booksDAO.addBook(title, author, genre);
        Assert.assertEquals(1, booksDAO.getAll().size());
        Assert.assertEquals(1, dbUtils.getKnownAuthors().size());
        Assert.assertEquals(1, dbUtils.getKnownGenres().size());


        booksDAO.addBook(title, authorNew, genre);
        Assert.assertEquals(2, booksDAO.getAll().size());
        Assert.assertEquals(2, dbUtils.getKnownAuthors().size());
        Assert.assertEquals(1, dbUtils.getKnownGenres().size());

        booksDAO.addBook(title, author, genreNew);
        Assert.assertEquals(3, booksDAO.getAll().size());
        Assert.assertEquals(2, dbUtils.getKnownAuthors().size());
        Assert.assertEquals(2, dbUtils.getKnownGenres().size());
    }

    @Test
    public void consistentTest(){
        dbUtils.clearDB();
        Assert.assertEquals(0, booksDAO.getAll().size());
        Assert.assertEquals(0, dbUtils.getKnownAuthors().size());
        Assert.assertEquals(0, dbUtils.getKnownGenres().size());

        String title = "title";
        String author = "author";
        String genre = "genre";

        booksDAO.addBook(title, author, genre);
        final List<Book> books = booksDAO.getAll();
        Assert.assertEquals(1, books.size());

        final Book book = books.get(0);

        Assert.assertEquals(title, book.getTitle());
        Assert.assertEquals(1, book.getAuthors().size());
        Assert.assertEquals(author, book.getAuthors().get(0).getFullName());
        Assert.assertEquals(1, book.getGenres().size());
        Assert.assertEquals(genre, book.getGenres().get(0).getName());
    }

}
