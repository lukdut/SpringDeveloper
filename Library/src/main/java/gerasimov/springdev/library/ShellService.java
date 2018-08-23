package gerasimov.springdev.library;

import gerasimov.springdev.library.dao.LibraryRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class ShellService {

    private final LibraryRepository repository;

    ShellService(LibraryRepository repository) {
        this.repository = repository;
    }

    @ShellMethod("List all known books")
    public void list() {
        System.out.println(repository.getAll());
    }

    @ShellMethod("Add new book")
    @LogAfter("Book added")
    public void add(@ShellOption String title, @ShellOption String authors, @ShellOption String genres) {
        List<String> authorsList = Stream.of(authors.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        List<String> genresList = Stream.of(genres.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        repository.addBook(title, authorsList, genresList);
    }

    @ShellMethod("Find book by title")
    public void find(@ShellOption String title) {
        System.out.println(repository.findBooks(title));
    }

    @ShellMethod("Get all known authors")
    public void authors() {
        System.out.println(repository.getAuthors());
    }

    @ShellMethod("Get all known genres")
    public void genres() {
        System.out.println(repository.getGenres());
    }

    @ShellMethod("Add comment for book by ID")
    @LogAfter("Comment added")
    public void comment(@ShellOption String comment, @ShellOption String id) {
        UUID uuid = UUID.fromString(id);
        repository.addComment(uuid, comment);
    }
}
