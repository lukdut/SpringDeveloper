package gerasimov.springdev.nosqllibrary.facade;


import java.util.List;

public interface LibraryFacade {
    void addBook(String title, List<String> Authors, List<String> genres);

    String findBook(String title);

    void commentBook(String bookId, String text);

    String showBookInfo(String bookId);
}
