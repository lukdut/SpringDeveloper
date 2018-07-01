package gerasimov.springdev.exercise.one.mock;

import gerasimov.springdev.exercise.one.api.QuestionsFabric;
import gerasimov.springdev.exercise.one.models.Answer;
import gerasimov.springdev.exercise.one.models.Question;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by admin on 27.06.2018.
 */
public class QuestionFabricMock implements QuestionsFabric {
    private final boolean allRights;
    private final int amountOfQuestions;

    public QuestionFabricMock(boolean allRights, int amountOfQuestions) {

        this.allRights = allRights;
        this.amountOfQuestions = amountOfQuestions;
    }

    @Override
    public List<Question> getQuestions() {
        return IntStream.range(0, amountOfQuestions).mapToObj(questionNum ->
                new Question("Question_" + questionNum, IntStream.range(0, 1).mapToObj(
                        answerNum -> new Answer("Answer_" + answerNum, allRights))
                        .collect(Collectors.toList()))).collect(Collectors.toList());
    }
}
