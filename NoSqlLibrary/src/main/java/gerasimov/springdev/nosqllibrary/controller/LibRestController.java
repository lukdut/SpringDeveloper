package gerasimov.springdev.nosqllibrary.controller;

import gerasimov.springdev.nosqllibrary.facade.BookLibFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class LibRestController {
    private static final Logger LOG = LoggerFactory.getLogger(LibRestController.class);
    private final BookLibFacade libraryFacade;

    public LibRestController(@Qualifier("mongoBookLibFacade") BookLibFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
    }

    @GetMapping("/list")
    public List<Book> list() {
        return libraryFacade.allBooks();
    }

    @PostMapping("/add")
    public String add(@RequestBody Book book) {
        LOG.debug("adding book: " + book);
        return libraryFacade.addBook(book);
    }

    @DeleteMapping("/del")
    public void del(@RequestParam String id) {
        LOG.debug("deleting book with id=" + id);
        libraryFacade.deleteBook(id);
    }

    @PutMapping("/upd")
    public void update(@RequestBody Book book) {
        LOG.debug("Updating book " + book);
        libraryFacade.updateBook(book);
    }
}
