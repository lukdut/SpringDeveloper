package gerasimov.springdev.library.dao;

import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JPABooksDao implements BooksDAO {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    @Transactional
    public synchronized void addBook(String title, List<String> authors, List<String> genres) {
        final List<Author> authorsList = authors.stream()
                .map(this::storeAuthor)
                .collect(Collectors.toList());
        final List<Genre> genresList = genres.stream()
                .map(this::storeGenre)
                .collect(Collectors.toList());

        TypedQuery<Book> query = em.createQuery("SELECT b from Book b where b.title = :title", Book.class);
        query.setParameter("title", title);
        List<Book> storedBooks = query.getResultList();
        boolean alreadyExists = storedBooks.stream()
                .anyMatch(storedBook ->
                        storedBook.getAuthors().containsAll(authors) && authors.containsAll(storedBook.getAuthors())
                                && storedBook.getGenres().containsAll(genres) && genres.containsAll(storedBook.getGenres()));
        if (alreadyExists) {
            System.out.println("book already exists!");
        } else {
            em.persist(new Book(title, authorsList, genresList));
        }
    }


    @Override
    public List<Author> getAuthors() {
        return null;
    }

    @Override
    public List<Genre> getGenres() {
        return null;
    }

    private synchronized Author storeAuthor(String name) {
        TypedQuery<Author> query = em.createQuery("SELECT a from Author a where a.fullName = :name", Author.class);
        query.setParameter("name", name);
        Author author;
        try {
            author = query.getSingleResult();
        } catch (NoResultException e) {
            author = new Author(name);
            em.persist(author);
        }
        return author;
    }

    private synchronized Genre storeGenre(String name) {
        TypedQuery<Genre> query = em.createQuery("SELECT g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        Genre genre;
        try {
            genre = query.getSingleResult();
        } catch (NoResultException e) {
            genre = new Genre(name);
            em.persist(genre);
        }
        return genre;
    }
}
