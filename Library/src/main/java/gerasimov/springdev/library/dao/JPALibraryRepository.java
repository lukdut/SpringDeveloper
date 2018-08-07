package gerasimov.springdev.library.dao;

import gerasimov.springdev.library.model.Author;
import gerasimov.springdev.library.model.Book;
import gerasimov.springdev.library.model.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Author addAuthor(String name) {
        return null;
    }

    @Override
    public Book addBook(String title, List<UUID> authors, List<UUID> genres) {
        return null;
    }

    @Override
    public Optional<Author> findAuthor(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Genre> findGenre(String name) {
        String preparedName = name.trim().toLowerCase();
        TypedQuery<Genre> query = em.createQuery("select a from Genre a where a.name = :name", Genre.class);
        query.setParameter("name", preparedName);
        List<Genre> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    @Override
    public Optional<List<Book>> findBooks(String title) {
        return Optional.empty();
    }


}
