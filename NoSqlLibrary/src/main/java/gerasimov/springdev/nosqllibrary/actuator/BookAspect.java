package gerasimov.springdev.nosqllibrary.actuator;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class BookAspect {

    private final BooksMetric addedMetric;

    public BookAspect(BooksMetric addedMetric) {
        this.addedMetric = addedMetric;
    }

    @After("@annotation(gerasimov.springdev.nosqllibrary.actuator.annotation.BookAddition)")
    public void add() {
        addedMetric.bookAdded();
    }

    @After("@annotation(gerasimov.springdev.nosqllibrary.actuator.annotation.BookDeletion)")
    public void delete() {
        addedMetric.bookDeleted();
    }
}
