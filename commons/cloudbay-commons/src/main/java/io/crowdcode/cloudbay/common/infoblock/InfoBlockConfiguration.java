package io.crowdcode.cloudbay.common.infoblock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@Configuration
public class InfoBlockConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "io.crowdcode.cloudbay.common", name = "enable-info-block", havingValue = "true", matchIfMissing = true)
    public CommandLineRunner infoBlock(final ApplicationContext context) {
        return (args) -> {
            Environment env = context.getEnvironment();
            String protocol = (env.getProperty("server.ssl.key-store") != null) ? "https" : "http";
            String hostAddress = "localhost";
            try {
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                log.warn("The host name could not be determined, using `localhost` as fallback");
            }
            String serverPort = env.getProperty("server.port");
            if (serverPort == null || serverPort.isEmpty()) {
                serverPort = "8080";
            }

            String contextPath = env.getProperty("server.servlet.context-path");
            if (contextPath == null || contextPath.isEmpty()) {
                contextPath = "/";
            }

            log.info("\n----------------------------------------------------------\n\t" +
                            "Application '{}' is running! Access URLs:\n\t" +
                            "Local: \t\t{}://localhost:{}{}\n\t" +
                            "External: \t{}://{}:{}{}\n\t" +
                            "Profile(s): \t{}\n----------------------------------------------------------",
                    env.getProperty("spring.application.name"),
                    protocol,
                    serverPort,
                    contextPath,
                    protocol,
                    hostAddress,
                    serverPort,
                    contextPath,
                    env.getActiveProfiles());
        };

    }
}
