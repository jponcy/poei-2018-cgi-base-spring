
package hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hello.entity.User;
import hello.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping("user")
    public List<User> getAllAction() {
        return this.repo.findAll();
    }

    @GetMapping("user/{uname:^[a-zA-Z_]+$}")
    public User findByUsernameAction(@PathVariable String uname) {
        return this.repo.findByUsernameIgnoreCase(uname);
    }

    @GetMapping("user/{id:^\\d+$}")
    public User findAction(@PathVariable Long id) {
        return this.repo.findOne(id);
    }

    @GetMapping("user/first")
    public User firstAction() {
        List<User> elts = this.repo.findAll();
        return elts.size() == 0 ? null : elts.get(0);
    }
}
