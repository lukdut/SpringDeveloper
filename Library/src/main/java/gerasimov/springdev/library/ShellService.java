package gerasimov.springdev.library;

import gerasimov.springdev.library.dao.LibraryRepository;
import gerasimov.springdev.library.model.Genre;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
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


    }

    @ShellMethod("List all known authors")
    public void authors() {

    }

    @ShellMethod("Add new genre")
    public void addGenre(@ShellOption String name) {
        Genre genre = repository.addGenre(name);
        System.out.println("Genre " + genre.getName() + " id is:" + genre.getId());
    }
}
