package gerasimov.springdev.library;

import gerasimov.springdev.library.dao.BooksDAO;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellService {

    private final BooksDAO booksDAO;

    ShellService(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    @ShellMethod("List all known books")
    public void list() {
        System.out.println(booksDAO.getAll());
    }

    @ShellMethod("Add new book")
    public void add(@ShellOption String title, @ShellOption String authors, @ShellOption String genres) {
        booksDAO.addBook(title, authors, genres);
        System.out.println("done");
    }


}
