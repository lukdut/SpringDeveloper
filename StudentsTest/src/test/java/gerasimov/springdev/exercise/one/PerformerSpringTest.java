package gerasimov.springdev.exercise.one;

import gerasimov.springdev.exercise.one.api.AnswerProvider;
import gerasimov.springdev.exercise.one.api.MessagesProvider;
import gerasimov.springdev.exercise.one.api.QuestionsFabric;
import gerasimov.springdev.exercise.one.mock.FixedAnswerProvider;
import gerasimov.springdev.exercise.one.mock.QuestionFabricMock;
import gerasimov.springdev.exercise.one.mock.TestMessageProvider;
import gerasimov.springdev.exercise.one.models.Question;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PerformerSpringTest {

    @Configuration
    static class SpringTestConfiguration{
        @Bean
        public AnswerProvider answerProvider(){
            return new FixedAnswerProvider(0);
        }

        @Bean
        public QuestionsFabric questionsFabric(){
            return new QuestionFabricMock(true, 5);
        }

        @Bean
        public MessagesProvider messagesProvider(){
            return new TestMessageProvider();
        }

        @Bean
        public TestPerformer testPerformer(QuestionsFabric questionsFabric, AnswerProvider answerProvider, MessagesProvider messagesProvider){
            return new TestPerformer(questionsFabric, answerProvider, messagesProvider);
        }
    }

    @Autowired
    private TestPerformer testPerformer;

    @Test
    public void passedTest() throws Exception {
        final Map<Question, Boolean> result = testPerformer.performTest();

        Assert.assertEquals(5, result.size());
        Assert.assertTrue(result.values().stream().allMatch(Boolean::booleanValue));

    }
}
