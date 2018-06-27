package gerasimov.springdev.exercise.one.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by admin on 27.06.2018.
 */
public class FixedAnswerProvider implements Supplier<Optional<Collection<Integer>>> {

    private final Integer index;

    public FixedAnswerProvider(Integer index) {

        this.index = index;
    }

    @Override
    public Optional<Collection<Integer>> get() {
        return Optional.of(Collections.singletonList(index));
    }
}
