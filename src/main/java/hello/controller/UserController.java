
package hello.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private Validator validator;

    @GetMapping()
    public List<User> getAllAction() {
        return this.repo.findAll();
        /* With manual DTO (result type -> UserGetDTO):
         * return this.repo.findAll().stream().map(user -> new UserGetDTO(user)).collect(Collectors.toList());
         */
    }

    @GetMapping("{uname:^[a-zA-Z_]+$}")
    public User findByUsernameAction(@PathVariable String uname) {
        return this.repo.findByUsernameIgnoreCase(uname);
    }

    @GetMapping("{id:^\\d+$}")
    public ResponseEntity<User> findAction(@PathVariable Long id) {
        User entity = this.repo.findOne(id);
        ResponseEntity<User> result = null;

        if (entity == null) {
            result = ResponseEntity.notFound().build();
        } else {
            result = ResponseEntity.ok(entity);
        }

        return result;
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

    @PutMapping("{id:^\\d+$}")
    public @ResponseBody ResponseEntity<?> updateAction(
            @PathVariable Long id, @RequestBody User data) {
        ResponseEntity<?> result = null;
        User user = this.repo.findOne(id);

        if (user == null) {
            result = ResponseEntity.notFound().build();
        } else {
            if (data.getFirstname() != null) { // Accept "".
                if ("".equals(data.getFirstname()))
                    user.setFirstname(null);
                else
                    user.setFirstname(data.getFirstname());
            }

            if (data.getLastname() != null) { // Accept "".
                user.setLastname(data.getLastname());
            }

            if (data.getUsername() != null) { // Accept "".
                user.setUsername(data.getUsername());
            }

            if (data.getPassword() != null) { // Accept "".
                user.setPassword(data.getPassword());
            }

            Set<ConstraintViolation<User>> violations = this.validator.validate(user);

            if (violations == null || violations.isEmpty()) {
                this.repo.save(user);
                result = ResponseEntity.ok(user);
            } else {
                Map<String, List<String>> errors = new HashMap<>();

                violations.forEach(error -> {
                    String fieldName = error.getPropertyPath().toString();
                    String errorMessage = error.getMessage();

                    if (!errors.containsKey(fieldName)) {
                        errors.put(fieldName, new LinkedList<>());
                    }

                    errors.get(fieldName).add(errorMessage);
                });

                result = ResponseEntity.badRequest().body(errors);
            }
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
