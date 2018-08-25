package gerasimov.springdev.nosqllibrary.controller;

import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class LibController {

    private final LibraryFacade libraryFacade;

    public LibController(LibraryFacade libraryFacade) {

        this.libraryFacade = libraryFacade;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("books", libraryFacade.allBooks());
        return "list";
    }

    @GetMapping(value = {"/edit/{id}", "/edit"})
    public String edit(Model model, @PathVariable(required = false) String id) {
        if (id == null) {
            model.addAttribute("book", new Book());
            return "edit";
        } else {
            Optional<Book> book = libraryFacade.showBookInfo(id);
            if (book.isPresent()) {
                model.addAttribute("book", book.get());
                return "edit";
            }
            return "404";
        }
    }

    @PostMapping("/edit")
    public String editBook(Model model, Book book) {
        libraryFacade.addBook(book.getTitle(), new ArrayList<>(), new ArrayList<>());
        model.addAttribute("books", libraryFacade.allBooks());
        return "list";
    }
}
