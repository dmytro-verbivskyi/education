package boot.simplejpa;

import boot.simplejpa.dao.UserRepository;
import boot.simplejpa.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleJpaApp {

    private static final Logger LOG = LogManager.getLogger(SimpleJpaApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SimpleJpaApp.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a couple of customers
            final User u1 = repository.save(new User("Jack", "Bauer"));
            repository.save(new User("Chloe", "O'Brian"));
            repository.save(new User("Kim", "Bauer"));
            repository.save(new User("David", "Palmer"));
            repository.save(new User("Michelle", "Dessler"));

            // fetch all customers
            LOG.info("Customers found with findAll():");
            LOG.info("-------------------------------");
            for (User customer : repository.findAll()) {
                LOG.info(customer.toString());
            }
            LOG.info("");

            // fetch an individual customer by ID
            Long id = u1.getId();

            repository.findById(id)
                    .ifPresent(customer -> {
                        LOG.info("Customer found with findById({}):", id);
                        LOG.info("--------------------------------");
                        LOG.info(customer.toString());
                        LOG.info("");
                    });

            // fetch customers by last name
            LOG.info("Customer found with findByLastName('Bauer'):");
            LOG.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                LOG.info(bauer.toString());
            });
            LOG.info("");
        };
    }
}
