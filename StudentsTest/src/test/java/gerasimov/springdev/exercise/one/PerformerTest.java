package gerasimov.springdev.exercise.one;

import gerasimov.springdev.exercise.one.mock.FixedAnswerProvider;
import gerasimov.springdev.exercise.one.mock.QuestionFabricMock;
import gerasimov.springdev.exercise.one.mock.TestMessageProvider;
import gerasimov.springdev.exercise.one.models.Question;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by admin on 27.06.2018.
 */
public class PerformerTest {

    @Test
    public void passedTest() throws Exception {
        TestPerformer testPerformer = new TestPerformer(new QuestionFabricMock(true, 5), new FixedAnswerProvider(0), new TestMessageProvider());
        final Map<Question, Boolean> result = testPerformer.performTest();

        Assert.assertEquals(5, result.size());
        Assert.assertTrue(result.values().stream().allMatch(Boolean::booleanValue));

    }

    @Test
    public void failedTest() throws Exception {
        TestPerformer testPerformer = new TestPerformer(new QuestionFabricMock(false, 5), new FixedAnswerProvider(0), new TestMessageProvider());
        final Map<Question, Boolean> result = testPerformer.performTest();

        Assert.assertEquals(5, result.size());
        Assert.assertTrue(result.values().stream().noneMatch(Boolean::booleanValue));

    }
}
