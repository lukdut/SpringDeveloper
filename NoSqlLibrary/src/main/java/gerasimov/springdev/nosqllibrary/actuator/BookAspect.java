package gerasimov.springdev.nosqllibrary.actuator;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class BookAspect {

    private final BooksMetric addedMetric;
    private final LastAddedBookTimeEndpoint addedBookEndpoint;

    public BookAspect(BooksMetric addedMetric, LastAddedBookTimeEndpoint addedBookEndpoint) {
        this.addedMetric = addedMetric;
        this.addedBookEndpoint = addedBookEndpoint;
    }

    @After("@annotation(gerasimov.springdev.nosqllibrary.actuator.annotation.BookAddition)")
    public void add() {
        addedMetric.bookAdded();
        addedBookEndpoint.created();
    }

    @After("@annotation(gerasimov.springdev.nosqllibrary.actuator.annotation.BookDeletion)")
    public void delete() {
        addedMetric.bookDeleted();
    }
}
