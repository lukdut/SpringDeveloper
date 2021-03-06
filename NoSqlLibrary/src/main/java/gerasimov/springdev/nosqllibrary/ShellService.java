package gerasimov.springdev.nosqllibrary;


import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class ShellService {
    private static final Logger LOG = LoggerFactory.getLogger(ShellService.class);

    private final LibraryFacade repository;

    ShellService(LibraryFacade repository) {
        this.repository = repository;
    }

    @ShellMethod("add new book if not exists")
    public void add(@ShellOption String title, @ShellOption String authors, @ShellOption String genres) {
        repository.addBook(title,
                Stream.of(authors.split(",")).map(String::trim).collect(Collectors.toList()),
                Stream.of(genres.split(",")).map(String::trim).collect(Collectors.toList()));
    }

    @ShellMethod("find books by title")
    public void find(@ShellOption String title) {
        LOG.debug(repository.findBook(title));
    }

    @ShellMethod("add commentary for book by id")
    public void comment(@ShellOption String text, @ShellOption String id) {
        repository.commentBook(id, text);
    }

    @ShellMethod("show book info by id")
    public void info(@ShellOption String id) {
        LOG.debug(String.valueOf(repository.showBookInfo(id)));
    }
}
