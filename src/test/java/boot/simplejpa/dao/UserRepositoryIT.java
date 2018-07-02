package boot.simplejpa.dao;

import boot.simplejpa.SimpleJpaApp;
import boot.simplejpa.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SimpleJpaApp.class})
@Transactional
public class UserRepositoryIT {

    @Resource
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void findById() {
        User one = new User("John", "Lennon");
        userRepository.save(one);

        Long id = one.getId();
        Optional<User> userOptional = userRepository.findById(id);
        assertThat(userOptional).isPresent().hasValue(one);
    }

    @Test
    public void findByLastName() {
        User one = new User("John", "Lennon");
        userRepository.save(one);

        List<User> list = userRepository.findByLastName("Lennon");
        assertThat(list).hasSize(1).containsExactly(one);
    }

    @Test
    public void queryByExampleOfName() {
        userRepository.saveAll(Arrays.asList(
                new User("John", "Lennon"),
                new User("Paul", "McCartney"),
                new User("George", "Harrison"),
                new User("Ringo", "Starr"),
                new User("John", "Cena")
        ));

        User exampleOfJohn = new User();
        exampleOfJohn.setFirstName("John");

        List<User> list = userRepository.findAll(Example.of(exampleOfJohn));
        assertThat(list).hasSize(2)
                .extracting("lastName").contains("Lennon", "Cena");
    }

}