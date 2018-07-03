package gerasimov.springdev.exercise.one.impl;

import gerasimov.springdev.exercise.one.api.MessagesProvider;
import gerasimov.springdev.exercise.one.api.NameProvider;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by adm
 * 27.06.2018
 * Но это не точно
 */
@Service("nameProvider")
public class ConsoleInputNameProvider implements NameProvider {
    private final MessagesProvider messagesProvider;

    public ConsoleInputNameProvider(MessagesProvider messagesProvider) {
        this.messagesProvider = messagesProvider;
    }

    @Override
    public String getName() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(messagesProvider.getMessage("name.request"));
        try {
            return br.readLine();
        } catch (IOException e) {
            return messagesProvider.getMessage("name.default");
        }
    }
}
