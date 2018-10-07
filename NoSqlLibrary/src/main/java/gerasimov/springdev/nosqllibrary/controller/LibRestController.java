package gerasimov.springdev.nosqllibrary.controller;

import gerasimov.springdev.nosqllibrary.facade.BookLibFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class LibRestController {
    private final BookLibFacade libraryFacade;

    public LibRestController(BookLibFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
    }

    @GetMapping("/list")
    public List<Book> list() {
        return libraryFacade.allBooks();
    }

    @PostMapping("/add")
    public String add(@RequestBody Book book) {
        System.out.println("adding book: " + book);
        return libraryFacade.addBook(book);
    }

    @DeleteMapping("/del")
    public void del(@RequestParam String id) {
        System.out.println("deleting book with id=" + id);
        libraryFacade.deleteBook(id);
    }

    @PutMapping("/upd")
    public void update(@RequestBody Book book) {
        System.out.println("Updating book " + book);
        libraryFacade.updateBook(book);
    }
}
