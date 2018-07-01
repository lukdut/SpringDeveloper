package gerasimov.springdev.exercise.one;

import gerasimov.springdev.exercise.one.api.MessagesProvider;
import gerasimov.springdev.exercise.one.api.NameProvider;
import gerasimov.springdev.exercise.one.models.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MainService {

    private final NameProvider nameProvider;
    private final TestPerformer testPerformer;
    private final MessagesProvider messagesProvider;

    public MainService(NameProvider nameProvider, TestPerformer testPerformer, MessagesProvider messagesProvider) {
        this.nameProvider = nameProvider;
        this.testPerformer = testPerformer;
        this.messagesProvider = messagesProvider;
    }

    public void run() {
        String name = nameProvider.getName();

        Map<Question, Boolean> result;
        try {
            result = testPerformer.performTest();
        } catch (Exception e) {
            System.out.println(messagesProvider.getMessage("test.perform.failed", new String[]{e.getMessage()}));
            return;
        }

        final List<Question> wrongQuestions = result.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (wrongQuestions.isEmpty()) {
            System.out.println(messagesProvider.getMessage("test.passed", new String[]{name}));
        } else {
            System.out.println(messagesProvider.getMessage("test.failed", new String[]{name, wrongQuestions.toString()}));
        }
    }
}
