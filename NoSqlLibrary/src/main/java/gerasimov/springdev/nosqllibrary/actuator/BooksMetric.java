package gerasimov.springdev.nosqllibrary.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class BooksMetric {
    private final Counter addedCounter;
    private final Counter deletedCounter;

    public BooksMetric(MeterRegistry registry) {
        addedCounter = Counter
                .builder("books.added")
                .register(registry);

        deletedCounter = Counter
                .builder("books.deleted")
                .register(registry);
    }

    public void bookAdded() {
        addedCounter.increment();
    }

    public void bookDeleted() {
        deletedCounter.increment();
    }
}
