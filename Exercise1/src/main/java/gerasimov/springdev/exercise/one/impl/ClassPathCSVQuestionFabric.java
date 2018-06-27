package gerasimov.springdev.exercise.one.impl;

import gerasimov.springdev.exercise.one.Run;
import gerasimov.springdev.exercise.one.api.QuestionsFabric;
import gerasimov.springdev.exercise.one.models.Answer;
import gerasimov.springdev.exercise.one.models.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 27.06.2018.
 */
public class ClassPathCSVQuestionFabric implements QuestionsFabric {
    private final String file;

    public ClassPathCSVQuestionFabric(String file) {
        this.file = file;
    }

    @Override
    public List<Question> getQuestions() {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Run.class.getResourceAsStream(file), StandardCharsets.UTF_8));

        List<Question> questions = new ArrayList<>();
        try {
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
            System.out.println("Can not load questions!");
        }

        return questions;

       /* return IntStream.range(0, 3).mapToObj(questionNum ->
                new Question("Question_" + questionNum, IntStream.range(0, 5).mapToObj(
                        answerNum -> new Answer("Answer_" + answerNum, answerNum == 0))
                        .collect(Collectors.toList()))).collect(Collectors.toList());*/
    }
}
