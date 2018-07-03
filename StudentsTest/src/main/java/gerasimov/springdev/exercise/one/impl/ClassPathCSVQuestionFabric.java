package gerasimov.springdev.exercise.one.impl;

import gerasimov.springdev.exercise.one.Run;
import gerasimov.springdev.exercise.one.api.MessagesProvider;
import gerasimov.springdev.exercise.one.api.QuestionsFabric;
import gerasimov.springdev.exercise.one.models.Answer;
import gerasimov.springdev.exercise.one.models.Question;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 27.06.2018.
 */
@Service
public class ClassPathCSVQuestionFabric implements QuestionsFabric {
    private final MessagesProvider messagesProvider;

    public ClassPathCSVQuestionFabric(MessagesProvider messagesProvider) {
        this.messagesProvider = messagesProvider;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Run.class.getResourceAsStream(messagesProvider.getMessage("questions.file")), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null; ) {
                String[] questionParts = line.split(";");
                if (questionParts.length < 3) {
                    continue;
                }

                List<Answer> answers = new ArrayList<>(questionParts.length - 1);
                answers.add(new Answer(questionParts[1], true));
                for (int i = 2; i < questionParts.length; i++) {
                    answers.add(new Answer(questionParts[i], false));
                }
                questions.add(new Question(questionParts[0], answers));
            }
        } catch (IOException e) {
            System.out.println(messagesProvider.getMessage("questions.load.error"));
        }

        return questions;
    }
}
