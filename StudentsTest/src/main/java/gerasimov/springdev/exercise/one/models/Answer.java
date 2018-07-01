package gerasimov.springdev.exercise.one.models;

/**
 * Created by admin on 27.06.2018.
 */
public class Answer {
    private String answer;
    private boolean isCorrect;


    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return answer;
    }
}
