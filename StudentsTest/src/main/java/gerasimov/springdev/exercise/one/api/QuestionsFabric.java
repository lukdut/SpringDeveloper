package gerasimov.springdev.exercise.one.api;

import gerasimov.springdev.exercise.one.models.Question;

import java.util.List;

/**
 * Created by admin on 27.06.2018.
 */
public interface QuestionsFabric {
    List<Question> getQuestions();
}
