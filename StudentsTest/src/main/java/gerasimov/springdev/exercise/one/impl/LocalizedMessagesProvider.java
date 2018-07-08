package gerasimov.springdev.exercise.one.impl;

import gerasimov.springdev.exercise.one.ApplicationSettings;
import gerasimov.springdev.exercise.one.api.MessagesProvider;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public class LocalizedMessagesProvider implements MessagesProvider {
    private final MessageSource messageSource;
    private final Locale locale;

    public LocalizedMessagesProvider(MessageSource messageSource, ApplicationSettings settings) {
        this.messageSource = messageSource;
        this.locale = new Locale(settings.getLocale());
    }

    @Override
    public String getMessage(String msgId, Object[] params) {
        return messageSource.getMessage(msgId, params, locale);
    }

    @Override
    public String getMessage(String msgId) {
        return this.getMessage(msgId, new Object[]{});
    }
}
