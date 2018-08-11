package gerasimov.springdev.library;

import gerasimov.springdev.library.dao.LibraryRepository;
import gerasimov.springdev.library.model.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryApplicationTests {

    @Autowired
    private LibraryRepository booksDAO;

    @Test
    @DirtiesContext
    public void noAuthorGenresDuplicationTest() {
        String title = "title";
        List<String> author = Collections.singletonList("author");
        List<String> genre = Collections.singletonList("genre");
        List<String> authorNew = Collections.singletonList("authorNew");
        List<String> genreNew = Collections.singletonList("genreNew");

        booksDAO.addBook(title, author, genre);
        Assert.assertEquals(1, booksDAO.getAll().size());
        Assert.assertEquals(1, booksDAO.getAuthors().size());
        Assert.assertEquals(1, booksDAO.getGenres().size());


        booksDAO.addBook(title, authorNew, genre);
        Assert.assertEquals(2, booksDAO.getAll().size());
        Assert.assertEquals(2, booksDAO.getAuthors().size());
        Assert.assertEquals(1, booksDAO.getGenres().size());

        booksDAO.addBook(title, author, genreNew);
        Assert.assertEquals(3, booksDAO.getAll().size());
        Assert.assertEquals(2, booksDAO.getAuthors().size());
        Assert.assertEquals(2, booksDAO.getGenres().size());
    }

    @Test
    @DirtiesContext
    public void consistentTest() {
        Assert.assertEquals(0, booksDAO.getAll().size());
        Assert.assertEquals(0, booksDAO.getAuthors().size());
        Assert.assertEquals(0, booksDAO.getGenres().size());

        String title = "title";
        List<String> author = Collections.singletonList("author");
        List<String> genre = Collections.singletonList("genre");

        booksDAO.addBook(title, author, genre);
        final List<Book> books = booksDAO.getAll();
        Assert.assertEquals(1, books.size());

        final Book book = books.get(0);

        Assert.assertEquals(title, book.getTitle());
        Assert.assertEquals(1, book.getAuthors().size());
        Assert.assertEquals(author.get(0), book.getAuthors().get(0).getFullName());
        Assert.assertEquals(1, book.getGenres().size());
        Assert.assertEquals(genre.get(0), book.getGenres().get(0).getName());
    }

    @Test
    @DirtiesContext
    public void commentTest() {
        Assert.assertEquals(0, booksDAO.getAll().size());
        Assert.assertEquals(0, booksDAO.getAuthors().size());
        Assert.assertEquals(0, booksDAO.getGenres().size());

        String title = "title";
        List<String> author = Collections.singletonList("author");
        List<String> genre = Collections.singletonList("genre");

        booksDAO.addBook(title, author, genre);
        List<Book> books = booksDAO.getAll();
        Assert.assertEquals(1, books.size());

        Book book = books.get(0);

        booksDAO.addComment(book.getId(), "comment");

        books = booksDAO.findBooks(title);
        Assert.assertEquals(1, books.size());

        book = books.get(0);

        Assert.assertEquals(title, book.getTitle());
        Assert.assertEquals(1, book.getAuthors().size());
        Assert.assertEquals(author.get(0), book.getAuthors().get(0).getFullName());
        Assert.assertEquals(1, book.getGenres().size());
        Assert.assertEquals(genre.get(0), book.getGenres().get(0).getName());
        Assert.assertEquals("comment", book.getComments().get(0).getText());
    }
}
