package gerasimov.springdev.integration.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.File;

@Configuration
public class FlowConfig {

    @Bean
    @ServiceActivator(inputChannel = "allOrders")
    public MessageHandler fileWritingMessageHandler(
            @Value("${integration.output.dir}") String outputDir,
            @Value("${integration.output.all}") String fileName) {
        System.out.println("Using directory " + outputDir);
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(outputDir));
        handler.setFileNameGenerator(message -> fileName);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);
        return handler;
    }

    @Bean
    public MessageChannel orderChannel() {
        return MessageChannels.direct("orderChannel").get();
    }

    @Bean
    public MessageChannel allOrders() {
        return MessageChannels.direct("allOrders").get();
    }

    @Bean
    public IntegrationFlow orderFlow(@Qualifier("orderChannel") MessageChannel input) {
        return f -> f.channel(input)
                .transform(o -> o.toString().getBytes())
                .channel("allOrders");
    }
}
