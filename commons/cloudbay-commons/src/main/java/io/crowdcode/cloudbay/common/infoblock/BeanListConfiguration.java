package io.crowdcode.cloudbay.common.infoblock;

import io.crowdcode.cloudbay.common.AnsiColor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@Configuration
public class BeanListConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = "io.crowdcode.cloudbay.common",
            name = "print-bean-list",
            havingValue = "true",
            matchIfMissing = true)
    public CommandLineRunner longBeansList(ApplicationContext context) {
        return (args) -> Arrays.stream(context.getBeanDefinitionNames())
                .map(AnsiColor::purple)
                .forEach(log::info);
    }
}
