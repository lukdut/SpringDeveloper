package gerasimov.springdev.exercise.one.impl;

import gerasimov.springdev.exercise.one.api.NameProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by adm
 * 27.06.2018
 * Но это не точно
 */
public class ConsoleInputNameProvider implements NameProvider {
    @Override
    public String getName() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Tell your first and last name: ");
        try {
            return br.readLine();
        } catch (IOException e) {
            return "Duuude";
        }
    }
}
