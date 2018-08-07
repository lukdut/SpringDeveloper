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
        TypedQuery<Genre> query = em.createQuery("select a from Genre a where a.name = :name", Genre.class);
        query.setParameter("name", name);
        List<Genre> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            Genre genre = new Genre(name);
            em.persist(genre);
            return genre;
        } else {
            return resultList.get(0);
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
        return Optional.empty();
    }

    @Override
    public Optional<List<Book>> findBooks(String title) {
        return Optional.empty();
    }


}
