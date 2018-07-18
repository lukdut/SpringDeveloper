package gerasimov.springdev.exercise.one;

import gerasimov.springdev.exercise.one.api.AnswerProvider;
import gerasimov.springdev.exercise.one.api.MessagesProvider;
import gerasimov.springdev.exercise.one.api.QuestionsFabric;
import gerasimov.springdev.exercise.one.models.Answer;
import gerasimov.springdev.exercise.one.models.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by admin on 27.06.2018.
 */
@Service
public class TestPerformer {
    private final QuestionsFabric questionsFabric;
    private final AnswerProvider answerProvider;
    private final MessagesProvider messagesProvider;

    public TestPerformer(QuestionsFabric questionsFabric, AnswerProvider answerProvider, MessagesProvider messagesProvider) {
        this.questionsFabric = questionsFabric;
        this.answerProvider = answerProvider;
        this.messagesProvider = messagesProvider;
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

            System.out.println(messagesProvider.getMessage("answer.choose"));

            Optional<Collection<Integer>> answers = null;
            do {
                if (answers != null) {
                    System.out.println(messagesProvider.getMessage("input.wrong"));
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
