package gerasimov.springdev.library;

import gerasimov.springdev.library.dao.BooksDAO;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class ShellService {

    private final BooksDAO booksDAO;

    ShellService(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    @ShellMethod("List all known books")
    public void list() {
        System.out.println(booksDAO.getAll());
    }

    @ShellMethod("Add new book")
    public void add(@ShellOption String title, @ShellOption String authors, @ShellOption String genres) {
        List<String> authorsList = Stream.of(authors.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        List<String> genresList = Stream.of(genres.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        booksDAO.addBook(title, authorsList, genresList);
        System.out.println("done");
    }

    @ShellMethod("List all known authors")
    public void authors() {
        System.out.println(booksDAO.getAuthors());
    }
}
