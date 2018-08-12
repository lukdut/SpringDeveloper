package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Author;
import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.repository.AuthorRepository;
import gerasimov.springdev.nosqllibrary.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class MongoLibraryFacade implements LibraryFacade {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public MongoLibraryFacade(
            BookRepository bookRepository,
            AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public synchronized void addBook(String title, List<String> authors, List<String> genres) {
        Optional<Book> existedBook = bookRepository.findByTitleIgnoreCase(title);

        Set<String> authorsId = authors.stream()
                .map(name -> {
                    Optional<Author> foundAuthor = authorRepository.findByFullNameIgnoreCase(name);
                    if (foundAuthor.isPresent()) {
                        return foundAuthor.get();
                    } else {
                        Author author = new Author(name);
                        authorRepository.save(author);
                        return author;
                    }
                })
                .map(Author::getId)
                .collect(Collectors.toSet());

        if (existedBook.isPresent() && existedBook.get().getAuthorIds().equals(authorsId)) {
            System.out.println("Book already exists");
        } else {
            Book book = new Book(title.trim());
            book.setAuthorIds(authorsId);
            bookRepository.insert(book);
        }
    }

    @Override
    public String findBook(String title) {
        return booksToString(bookRepository.findByTitleLike(title.trim()));
    }

    @Override
    public void commentBook(String bookId, String text) {
        bookRepository.findById(bookId).ifPresent(book -> {
            // book.
            //TODO
        });
    }

    @Override
    public String showBookInfo(String bookId) {
        //TODO
        return null;
    }

    private String booksToString(List<Book> books) {
        return books.stream()
                .map(book -> {
                    String authors = book.getAuthorIds().stream()
                            .map(authorRepository::findById)
                            .map(Optional::get)
                            .map(Author::getFullName)
                            .collect(Collectors.joining(", "));

                    return book.getTitle() + " (" + authors + ") " + book.getId();
                }).collect(Collectors.joining("; "));
    }
}
