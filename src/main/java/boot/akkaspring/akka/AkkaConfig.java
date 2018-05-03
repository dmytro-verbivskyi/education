package boot.akkaspring.akka;

import akka.actor.ActorSystem;
import boot.akkaspring.akka.extension.SpringExtension;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class AkkaConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    SpringExtension springExtension;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("KMT", akkaConfiguration());

        // Initialize the application context in the Akka Spring Extension
        springExtension.initialize(applicationContext);
        return system;
    }

    /**
     * Read configuration from application.conf file
     */
    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }
}
