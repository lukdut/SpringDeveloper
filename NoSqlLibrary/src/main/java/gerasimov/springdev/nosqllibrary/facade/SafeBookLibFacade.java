package gerasimov.springdev.nosqllibrary.facade;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import gerasimov.springdev.nosqllibrary.model.Book;

import java.util.List;
import java.util.function.Function;

public class SafeBookLibFacade implements BookLibFacade {
    private static final String DB_READ = "DB_READ_DATA";
    private static final String DB_WRITE = "DB_WRITE_DATA";
    private final BookLibFacade bookLibFacade;

    public SafeBookLibFacade(MongoLibraryFacade bookLibFacade) {
        this.bookLibFacade = bookLibFacade;
    }

    @Override
    public List<Book> allBooks() {
        return new RepoCommand<>(DB_READ, null, o -> bookLibFacade.allBooks()).execute();
    }

    @Override
    public void deleteBook(String id) {
        new RepoCommand<>(DB_WRITE, id, s -> {
            bookLibFacade.deleteBook(s);
            return null;
        }).execute();
    }

    @Override
    public void updateBook(Book book) {
        new RepoCommand<>(DB_WRITE, book, b -> {
            bookLibFacade.updateBook(b);
            return null;
        }).execute();
    }

    @Override
    public String addBook(Book book) {
        return new RepoCommand<>(DB_WRITE, book, bookLibFacade::addBook).execute();
    }

    private class RepoCommand<T, R> extends HystrixCommand<R> {
        private final Function<T, R> command;
        private final T argument;

        RepoCommand(String group, T argument, Function<T, R> command) {
            super(HystrixCommandGroupKey.Factory.asKey(group));
            this.command = command;
            this.argument = argument;
        }

        @Override
        protected R run() {
            return command.apply(argument);
        }
    }
}
