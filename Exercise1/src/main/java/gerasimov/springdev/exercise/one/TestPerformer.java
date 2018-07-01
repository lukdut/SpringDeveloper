package gerasimov.springdev.exercise.one;

import gerasimov.springdev.exercise.one.api.AnswerProvider;
import gerasimov.springdev.exercise.one.api.QuestionsFabric;
import gerasimov.springdev.exercise.one.models.Answer;
import gerasimov.springdev.exercise.one.models.Question;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by admin on 27.06.2018.
 */
public class TestPerformer {
    private final QuestionsFabric questionsFabric;
    private final AnswerProvider answerProvider;

    TestPerformer(QuestionsFabric questionsFabric, AnswerProvider answerProvider) {
        this.questionsFabric = questionsFabric;
        this.answerProvider = answerProvider;
    }

    Map<Question, Boolean> performTest() throws Exception {
        List<Question> questions = questionsFabric.getQuestions();
        Collections.shuffle(questions);
        Map<Question, Boolean> results = questions.stream().collect(Collectors.toMap(question -> question, question -> {
            List<Answer> variants = question.getAnswers();
            Collections.shuffle(variants);

            System.out.println(question.getQuestion());
            IntStream.range(0, variants.size()).forEach(index ->
                    System.out.println(index + ": " + variants.get(index).getAnswer()));

            System.out.println("Please, chose all correct answer(s) (','-separated):");

            Optional<Collection<Integer>> answers = null;
            do {
                if (answers != null) {
                    System.out.println("incorrect input!");
                }
                answers = answerProvider.getAnswers();
            }
            while (!answers.isPresent() || answers.get().stream().anyMatch(integer -> integer >= variants.size() || integer < 0));

            return answers.get().stream()
                    .allMatch(answerIndex -> variants.get(answerIndex).isCorrect());
        }));

        answerProvider.close();
        return results;
    }
}
