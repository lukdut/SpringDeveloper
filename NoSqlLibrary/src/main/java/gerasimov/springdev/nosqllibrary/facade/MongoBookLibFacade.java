package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.repository.BookRepository;

import java.util.List;

public class MongoBookLibFacade implements BookLibFacade {
    final BookRepository bookRepository;

    public MongoBookLibFacade(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public String addBook(Book book) {
        return bookRepository.save(book).getId();
    }
}
