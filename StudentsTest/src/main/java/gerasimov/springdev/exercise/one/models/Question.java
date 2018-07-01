package gerasimov.springdev.exercise.one.models;

import java.util.List;

/**
 * Created by admin on 27.06.2018.
 */
public class Question {
    private String question;
    private List<Answer> answers;

    public Question(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return question;
    }
}
