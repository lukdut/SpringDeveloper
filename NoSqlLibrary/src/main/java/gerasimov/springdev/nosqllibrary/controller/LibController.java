package gerasimov.springdev.nosqllibrary.controller;

import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
