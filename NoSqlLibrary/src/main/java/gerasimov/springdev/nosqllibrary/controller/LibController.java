package gerasimov.springdev.nosqllibrary.controller;

import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.model.CommentPlaceholder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Optional;

//@Controller
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
        model.addAttribute("newComment", new CommentPlaceholder());
        if (id == null) {
            model.addAttribute("book", new Book());
            return "edit";
        } else {
            Optional<Book> foundBook = libraryFacade.showBookInfo(id);
            if (foundBook.isPresent()) {
                model.addAttribute("book", foundBook.get());
                return "edit";
            }
            return "404";
        }
    }

    @PostMapping("/edit")
    public String editBook(Model model, Book book) {
        if (book.getId() == null || book.getId().isEmpty()) {
            libraryFacade.addBook(book.getTitle(), new ArrayList<>(), new ArrayList<>());
        } else {
            libraryFacade.updateBook(book);
        }
        model.addAttribute("books", libraryFacade.allBooks());
        return "redirect:/";
    }

    @PostMapping("/comment")
    public String comment(Model model, Book book, CommentPlaceholder newComment) {
        libraryFacade.commentBook(book.getId(), newComment.getText());
        System.out.println("adding comment " + newComment.getText());
        model.addAttribute("newComment", new CommentPlaceholder());
        model.addAttribute("book", book);
        return "redirect:/edit/" + book.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable String id) {
        libraryFacade.deleteBook(id);
        model.addAttribute("books", libraryFacade.allBooks());
        return "redirect:/";
    }
}
