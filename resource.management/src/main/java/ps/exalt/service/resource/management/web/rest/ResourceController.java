package ps.exalt.service.resource.management.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/")
    public String greetingAPI(){
        return "hello world";
    }


}
