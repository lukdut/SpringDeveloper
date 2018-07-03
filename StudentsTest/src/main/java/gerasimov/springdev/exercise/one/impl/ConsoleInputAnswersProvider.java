package gerasimov.springdev.exercise.one.impl;

import gerasimov.springdev.exercise.one.api.AnswerProvider;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by admin on 27.06.2018.
 */
@Service
public class ConsoleInputAnswersProvider implements AnswerProvider {
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public Optional<Collection<Integer>> getAnswers() {
        System.out.print("-> ");
        try {
            String[] answers = br.readLine().split(",");
            try {
                return Optional.of(
                        Stream.of(answers)
                                .map(String::trim)
                                .map(Integer::parseInt)
                                .collect(Collectors.toList())
                );
            } catch (NumberFormatException nfe) {
                return Optional.empty();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void close() throws Exception {
        br.close();
    }
}
