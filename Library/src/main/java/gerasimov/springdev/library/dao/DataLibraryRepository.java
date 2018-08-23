package gerasimov.springdev.library.dao;

import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Comment;
import gerasimov.springdev.library.model.Genre;
import gerasimov.springdev.library.repository.AuthorsRepository;
import gerasimov.springdev.library.repository.BooksRepository;
import gerasimov.springdev.library.repository.CommentRepository;
import gerasimov.springdev.library.repository.GenresRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DataLibraryRepository implements LibraryRepository {

    private final AuthorsRepository authorsRepository;
    private final GenresRepository genresRepository;
    private final BooksRepository booksRepository;
    private final CommentRepository commentRepository;

    public DataLibraryRepository(
            AuthorsRepository authorsRepository,
            GenresRepository genresRepository,
            BooksRepository booksRepository,
            CommentRepository commentRepository) {
        this.authorsRepository = authorsRepository;
        this.genresRepository = genresRepository;
        this.booksRepository = booksRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public Genre addGenre(String name) {
        Optional<Genre> found = findGenre(name);
        if (found.isPresent()) {
            return found.get();
        } else {
            Genre genre = new Genre(name.trim().toLowerCase());
            genresRepository.save(genre);
            return genre;
        }
    }

    @Override
    @Transactional
    public synchronized Author addAuthor(String name) {
        Optional<Author> foundAuthor = findAuthor(name);
        if (foundAuthor.isPresent()) {
            return foundAuthor.get();
        } else {
            Author author = new Author(name.trim().toLowerCase());
            authorsRepository.save(author);
            return author;
        }
    }

    @Override
    public List<Book> getAll() {
        return booksRepository.findAll();
    }

    @Override
    public void addBook(String title, List<String> authors, List<String> genres) {
        final List<Author> authorsList = authors.stream()
                .map(this::addAuthor)
                .collect(Collectors.toList());
        final List<Genre> genresList = genres.stream()
                .map(this::addGenre)
                .collect(Collectors.toList());
        booksRepository.save(new Book(title, authorsList, genresList));
    }

    @Override
    public List<Author> getAuthors() {
        return authorsRepository.findAll();
    }

    @Override
    public List<Genre> getGenres() {
        return genresRepository.findAll();
    }

    @Override
    public Optional<Author> findAuthor(String name) {
        return authorsRepository.findAuthorByFullName(name.trim().toLowerCase());
    }

    @Override
    public Optional<Genre> findGenre(String name) {
        return genresRepository.findByName(name.trim().toLowerCase());
    }

    @Override
    public List<Book> findBooks(String title) {
        return booksRepository.findAllByTitle(title.trim().toLowerCase());
    }

    @Override
    @Transactional
    public synchronized void addComment(UUID bokId, String commentString) {
        Book book = booksRepository.findById(bokId);
        Comment comment = new Comment(commentString);
        commentRepository.save(comment);
        book.addComment(comment);
        booksRepository.save(book);
    }
}
