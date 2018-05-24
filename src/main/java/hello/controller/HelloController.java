
package hello.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // @RequestMapping(value = "hello", method = RequestMethod.GET)
    @GetMapping("hello")
    public Map<String, String> helloAction(
            @RequestParam(defaultValue = "world") String name) {
        return Collections.singletonMap("message", "Hello " + name);
    }
}
