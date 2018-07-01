package gerasimov.springdev.exercise.one;

import gerasimov.springdev.exercise.one.api.NameProvider;
import gerasimov.springdev.exercise.one.models.Question;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by admin on 27.06.2018.
 */

@Configuration
@ComponentScan
public class Run {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Run.class);
        TestPerformer testPerformer = context.getBean(TestPerformer.class);
        NameProvider nameProvider = context.getBean("nameProvider", NameProvider.class);

        String name = nameProvider.getName();

        Map<Question, Boolean> result;
        try {
            result = testPerformer.performTest();
        } catch (Exception e) {
            System.out.println("Can not perform test: " + e.getMessage());
            return;
        }

        final List<Question> wrongQuestions = result.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (wrongQuestions.isEmpty()) {
            System.out.println("Good job, " + name + "!");
        } else {
            System.out.println(name + ", you failed test, incorrect answers received for questions: " + wrongQuestions);
        }
    }
}
