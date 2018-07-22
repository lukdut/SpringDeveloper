package gerasimov.springdev.exercise.one;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellService {
    private final MainService mainService;

    public ShellService(MainService mainService) {
        this.mainService = mainService;
    }

    @ShellMethod("Start testing procedure")
    public void begin() {
        mainService.run();
    }
}
