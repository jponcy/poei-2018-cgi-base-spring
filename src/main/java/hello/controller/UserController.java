
package hello.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.entity.User;
import hello.repository.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping
    public List<User> getAllAction() {
        return this.repo.findAll();
    }

    @GetMapping("{uname:^[a-zA-Z_]+$}")
    public User findByUsernameAction(@PathVariable String uname) {
        return this.repo.findByUsernameIgnoreCase(uname);
    }

    @GetMapping("{id:^\\d+$}")
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
    @PostMapping
    public @ResponseBody ResponseEntity<?> createAction(
            @Valid @RequestBody User entity) {
        ResponseEntity<?> result;

        // !Strings.isNullOrEmpty(username) test already done by required = true.
        if (this.repo.findByUsernameIgnoreCase(entity.getUsername()) == null) { // Check unique username constraint.
            this.repo.save(entity);

            result = ResponseEntity.ok(entity.getId());
        } else { // Has no user with same username.
            result = ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", entity.getUsername() + " already used"));
        }

        return result;
    }

    @PostMapping("{id:^\\d+$}")
    public @ResponseBody ResponseEntity<?> updateAction(
            @PathVariable Long id, @Valid @RequestBody User data, HttpServletResponse response) {
        ResponseEntity<?> result = null;
        User user = this.repo.findOne(id);

        if (user == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            data.setId(id);
            this.repo.save(data);
        }

        return result;
    }

    @DeleteMapping("{id:^\\d+$}")
    public void deleteAction(@PathVariable Long id, HttpServletResponse response) {
        User entity = this.repo.findOne(id);

        if (entity == null) {
            // throw new NotFoundException();
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            this.repo.delete(entity);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }

    @GetMapping("first")
    public User firstAction() {
        List<User> elts = this.repo.findAll();
        return elts.size() == 0 ? null : elts.get(0);
    }
}
