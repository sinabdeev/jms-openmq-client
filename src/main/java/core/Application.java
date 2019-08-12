package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.lang.NonNull;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sun.messaging.ConnectionConfiguration;


@EnableJms
@SpringBootApplication
@Configuration
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ConnectionFactory connectionFactory () throws JMSException {
        com.sun.messaging.ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
        setupProperties(cf);
        return cf;
    }

    private void setupProperties(com.sun.messaging.ConnectionFactory cf) throws JMSException {
        cf.setProperty(ConnectionConfiguration.imqBrokerHostName, "localhost");
        cf.setProperty(ConnectionConfiguration.imqBrokerHostPort, "7676");
        cf.setProperty(ConnectionConfiguration.imqDefaultUsername , "admin");
        cf.setProperty(ConnectionConfiguration.imqDefaultPassword , "admin");
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(@NonNull final ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {

            try {

                LocalDateTime today = LocalDateTime.now();
                log.info("Hello, World!");
                log.info(today.format(DateTimeFormatter.ISO_DATE_TIME));
                log.info("Hello, World!");

            } catch (Exception e) {

                e.printStackTrace();
                log.error(e.getMessage());
                log.error(e.getLocalizedMessage());

            } finally {


            }
        };
    }



}
