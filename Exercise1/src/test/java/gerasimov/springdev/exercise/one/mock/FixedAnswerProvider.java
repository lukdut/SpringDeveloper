package gerasimov.springdev.exercise.one.mock;

import gerasimov.springdev.exercise.one.api.AnswerProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by admin on 27.06.2018.
 */
public class FixedAnswerProvider implements AnswerProvider {

    private final Integer index;

    public FixedAnswerProvider(Integer index) {

        this.index = index;
    }

    @Override
    public Optional<Collection<Integer>> getAnswers() {
        return Optional.of(Collections.singletonList(index));
    }

    @Override
    public void close() {
    }
}
