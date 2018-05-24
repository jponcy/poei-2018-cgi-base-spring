
package hello.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    /**
     * Create a new user.
     *
     * This method check the unique constraint about user name.
     *
     * Example:
     * <pre>
     * $ curl -X POST http://localhost:8080/user/ -d "username=peterpan"
     * > {"createObjectId":6}
     * # OR
     * > {"error":"peterpan already used"} # If username already used.
     *
     * </pre>
     *
     * @param username
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "user")
    public @ResponseBody Map<String, Object> createAction(@RequestParam String username, HttpServletResponse response) {
        Map<String, Object> result;

        // !Strings.isNullOrEmpty(username) test already done by required = true.
        if (this.repo.findByUsernameIgnoreCase(username) == null) { // Check unique username constraint.
            User entity = new User(username);

            this.repo.save(entity);

            response.setStatus(HttpStatus.CREATED.value());
            result = Collections.singletonMap("createObjectId", entity.getId());
        } else { // Has no user with same username.
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = Collections.singletonMap("error", username + " already used");
        }

        return result;
    }

    @GetMapping("user/first")
    public User firstAction() {
        List<User> elts = this.repo.findAll();
        return elts.size() == 0 ? null : elts.get(0);
    }
}
