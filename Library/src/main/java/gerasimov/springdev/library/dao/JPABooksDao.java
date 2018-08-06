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
    public void addBook(String title, List<String> authors, List<String> genres) {


        final List<Author> authorsList = authors.stream()
                .map(name -> {
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
                }).collect(Collectors.toList());


        /*final List<Genre> genresList = genres.stream()
                .map(this::storeGenre)
                .collect(Collectors.toList());*/
    }

    @Override
    public List<Author> getAuthors() {
        return null;
    }

    @Override
    public List<Genre> getGenres() {
        return null;
    }
}
