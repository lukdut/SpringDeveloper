package gerasimov.springdev.nosqllibrary.controller;

import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibRestController {
    private final LibraryFacade libraryFacade;

    public LibRestController(LibraryFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
    }

    @GetMapping("/")
    public List<Book> list() {
        return libraryFacade.allBooks();
    }

    @PostMapping("/add")
    public void add(@RequestBody Book book){
        System.out.println("adding book: " + book);
        libraryFacade.addBook(book);
    }

    @DeleteMapping
    public void del(@RequestParam String id){
        System.out.println("deleting book with id=" + id);
        libraryFacade.deleteBook(id);
    }
}
