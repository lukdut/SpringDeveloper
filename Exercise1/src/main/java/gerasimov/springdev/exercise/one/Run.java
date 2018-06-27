package gerasimov.springdev.exercise.one;

import gerasimov.springdev.exercise.one.models.Question;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by admin on 27.06.2018.
 */
public class Run {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");
        TestPerformer testPerformer = context.getBean(TestPerformer.class);
        Map<Question, Boolean> result = testPerformer.performTest();

        final List<Question> wrongQuestions = result.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (wrongQuestions.isEmpty()) {
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong questions: " + wrongQuestions);
        }
    }
}
