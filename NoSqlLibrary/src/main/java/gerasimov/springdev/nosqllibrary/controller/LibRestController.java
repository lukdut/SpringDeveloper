package gerasimov.springdev.nosqllibrary.controller;

import gerasimov.springdev.nosqllibrary.facade.ReactiveLibFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class LibRestController {
    private final ReactiveLibFacade libraryFacade;

    public LibRestController(ReactiveLibFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
    }

    @GetMapping("/list")
    public Flux<Book> list() {
        return libraryFacade.allBooks();
    }

    @PostMapping("/add")
    public Mono<String> add(@RequestBody Book book) {
        System.out.println("adding book: " + book);
        return libraryFacade.addBook(book);
    }

    @DeleteMapping("/del")
    public Mono<Void> del(@RequestParam String id) {
        System.out.println("deleting book with id=" + id);
        return libraryFacade.deleteBook(id);
    }

    @PutMapping("/upd")
    public Mono<Void> update(@RequestBody Book book) {
        System.out.println("Updating book " + book);
        return libraryFacade.updateBook(book);
    }
}
