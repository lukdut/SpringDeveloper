package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Author;
import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.model.Genre;
import gerasimov.springdev.nosqllibrary.repository.AuthorRepository;
import gerasimov.springdev.nosqllibrary.repository.BookRepository;
import gerasimov.springdev.nosqllibrary.repository.GenresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MongoLibraryFacade extends MongoBookLibFacade implements LibraryFacade {
    private static final Logger LOG = LoggerFactory.getLogger(MongoLibraryFacade.class);
    private final AuthorRepository authorRepository;
    private final GenresRepository genresRepository;
    private final MongoTemplate mongoTemplate;

    public MongoLibraryFacade(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            GenresRepository genresRepository,
            MongoTemplate mongoTemplate) {
        super(bookRepository);
        this.authorRepository = authorRepository;
        this.genresRepository = genresRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    //synchronized тк можно получить дубликат при попытке создать одновременно одинаковую книгу
    //(одинаковые авторы и название)
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
            LOG.debug("Book already exists");
        } else {
            Set<String> genreIds = genres.stream()
                    .map(name -> {
                        Optional<Genre> found = genresRepository.findByNameIgnoreCase(name);
                        if (found.isPresent()) {
                            return found.get();
                        } else {
                            Genre genre = new Genre(name);
                            genresRepository.save(genre);
                            return genre;
                        }
                    })
                    .map(Genre::getId)
                    .collect(Collectors.toSet());

            Book book = new Book(title.trim());
            book.setAuthorIds(authorsId);
            book.setGenresIds(genreIds);
            bookRepository.insert(book);
        }
    }

    @Override
    public String findBook(String title) {
        return booksToString(bookRepository.findByTitleLike(title.trim()));
    }

    @Override
    public void commentBook(String bookId, String text) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookId));
        Update update = new Update();
        update.addToSet("comments", text);
        mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public Optional<Book> showBookInfo(String bookId) {
        return bookRepository.findById(bookId);
    }

    private String booksToString(List<Book> books) {
        return books.stream()
                .map(this::getBookShortInfo)
                .collect(Collectors.joining("\n"));
    }

    private String getBookShortInfo(Book book) {
        String authors = book.getAuthorIds().stream()
                .map(authorRepository::findById)
                .map(Optional::get)
                .map(Author::getFullName)
                .collect(Collectors.joining(", "));

        String genres = book.getGenresIds().stream()
                .map(genresRepository::findById)
                .map(Optional::get)
                .map(Genre::getName)
                .collect(Collectors.joining(", "));

        return book.getTitle() + " (" + authors + "), [" + genres + "] " + book.getId();
    }
}
