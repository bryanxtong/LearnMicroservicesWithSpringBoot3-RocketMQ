package microservices.book.multiplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.JacksonModule;
import tools.jackson.datatype.hibernate7.Hibernate7Module;

@Configuration
public class JsonConfiguration {
    @Bean
    public JacksonModule hibernateModule() {
        return new Hibernate7Module();
    }

}
