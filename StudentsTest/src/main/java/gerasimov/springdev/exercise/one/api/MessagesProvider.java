package gerasimov.springdev.exercise.one.api;


public interface MessagesProvider {
    String getMessage(String msgId, Object[] params);

    String getMessage(String msgId);
}
