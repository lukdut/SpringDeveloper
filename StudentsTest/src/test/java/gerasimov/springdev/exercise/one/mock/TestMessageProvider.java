package gerasimov.springdev.exercise.one.mock;

import gerasimov.springdev.exercise.one.api.MessagesProvider;

/**
 * Created by adm
 * 02.07.2018
 * Но это не точно
 */
public class TestMessageProvider implements MessagesProvider {
    @Override
    public String getMessage(String msgId, Object[] params) {
        return msgId;
    }

    @Override
    public String getMessage(String msgId) {
        return msgId;
    }
}
