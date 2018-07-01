package gerasimov.springdev.exercise.one;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by admin on 27.06.2018.
 */

@PropertySource("classpath:default.properties")
@Configuration
@ComponentScan
public class Run {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Run.class);
        MainService mainService = context.getBean(MainService.class);
        mainService.run();
    }
}
