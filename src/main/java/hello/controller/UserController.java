
package hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
