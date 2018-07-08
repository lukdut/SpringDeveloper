package gerasimov.springdev.exercise.one;


import gerasimov.springdev.exercise.one.mock.FixedAnswerProvider;
import gerasimov.springdev.exercise.one.mock.QuestionFabricMock;
import gerasimov.springdev.exercise.one.mock.TestMessageProvider;
import org.junit.Test;

public class MainServiceTest {

    @Test
    public void noExceptionsTest() {
        TestPerformer testPerformer = new TestPerformer(new QuestionFabricMock(true, 5), new FixedAnswerProvider(0), new TestMessageProvider());

        MainService mainService = new MainService(() -> "username", testPerformer, new TestMessageProvider());

        mainService.run();
    }
}
