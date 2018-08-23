package gerasimov.springdev.library.dao;

import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Comment;
import gerasimov.springdev.library.model.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JPALibraryRepository implements LibraryRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public synchronized Genre addGenre(String name) {
        Optional<Genre> foundGenre = findGenre(name);
        if (foundGenre.isPresent()) {
            return foundGenre.get();
        } else {
            Genre genre = new Genre(name.trim().toLowerCase());
            em.persist(genre);
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
            em.persist(author);
            return author;
        }
    }

    @Override
    public Optional<Author> findAuthor(String name) {
        String preparedName = name.trim().toLowerCase();
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.fullName = :name", Author.class);
        query.setParameter("name", preparedName);
        List<Author> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    @Override
    public Optional<Genre> findGenre(String name) {
        String preparedName = name.trim().toLowerCase();
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", preparedName);
        List<Genre> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    @Transactional
    public void addBook(String title, List<String> authors, List<String> genres) {
        final List<Author> authorsList = authors.stream()
                .map(this::addAuthor)
                .collect(Collectors.toList());
        final List<Genre> genresList = genres.stream()
                .map(this::addGenre)
                .collect(Collectors.toList());
        em.persist(new Book(title, authorsList, genresList));
    }

    @Override
    public List<Book> findBooks(String title) {
        String preparedTitle = title.trim().toLowerCase();
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title", Book.class);
        query.setParameter("title", preparedTitle);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void addComment(UUID bokId, String commentString) {
        Book book = em.find(Book.class, bokId);
        Comment comment = new Comment(commentString);
        em.persist(comment);
        book.addComment(comment);
        em.persist(book);
    }

    @Override
    public List<Author> getAuthors() {
        return em.createQuery("select x from Author x", Author.class).getResultList();
    }

    @Override
    public List<Genre> getGenres() {
        return em.createQuery("select x from Genre x", Genre.class).getResultList();
    }
}
