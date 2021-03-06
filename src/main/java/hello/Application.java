
package hello;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hello.entity.User;
import hello.repository.UserRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRepository.save(Arrays.asList(this.user("isod"), this.user("theos"), this.user("harkline")));
        this.userRepository.flush();
    }

    private User user(String uname) {
        User result = new User(uname);
        result.setPassword(uname + "00");
        return result;
    }
}
