package io.crowdcode.cloudbay.catalog.config;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@org.springframework.context.annotation.Configuration
public class OpenTracingConfiguration {

    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public Tracer tracer() {
        io.opentracing.contrib.jdbc.TracingDriver.load();
        return new Configuration(serviceName)
                .withReporter(new Configuration.ReporterConfiguration()
                        .withSender(Configuration.SenderConfiguration.fromEnv()))
                .withSampler(Configuration.SamplerConfiguration.fromEnv())
                .getTracer();
    }


}
