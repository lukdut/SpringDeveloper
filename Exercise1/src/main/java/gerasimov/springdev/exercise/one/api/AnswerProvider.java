package gerasimov.springdev.exercise.one.api;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by adm
 * 01.07.2018
 * Но это не точно
 */
public interface AnswerProvider extends AutoCloseable {
    Optional<Collection<Integer>> getAnswers();
}
