package ps.exalt.service.resource.management.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ps.exalt.service.resource.management.model.Server;
import ps.exalt.service.resource.management.service.ResourceService;

import java.util.Map;

@RestController
public class ResourceController {

    @Autowired
    ResourceService resourceService;
    @GetMapping("/")
    public String greetingAPI(){
        return "hello world";
    }

    @GetMapping("/allocate/{numberOfGiga}")
    public Server allocateResources(@PathVariable("numberOfGiga") Long numberOfGiga) throws InterruptedException {
        return this.resourceService.allocateActiveServer(numberOfGiga);
    }


    @GetMapping("/{numberOfGiga}")
    public Map<String, String> reserveResources(@PathVariable("numberOfGiga") Long numberOfGiga) throws InterruptedException {
        return this.resourceService.reserveSpace(numberOfGiga);
    }


}
