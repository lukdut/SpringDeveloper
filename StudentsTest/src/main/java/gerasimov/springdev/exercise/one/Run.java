package gerasimov.springdev.exercise.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Created by admin on 27.06.2018.
 */


@SpringBootApplication
public class Run {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Run.class, args);
        MainService mainService = context.getBean(MainService.class);
        mainService.run();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
