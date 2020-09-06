package ps.exalt.service.resource.management.runnable;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ps.exalt.service.resource.management.enums.ServerState;
import ps.exalt.service.resource.management.model.Server;
import ps.exalt.service.resource.management.repository.ServerRepository;
import ps.exalt.service.resource.management.service.ResourceService;

@Component
@Scope("prototype")
public class Spinner implements Runnable{

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ServerRepository serverRepository;

    @SneakyThrows
    @Override
    public void run() {
        Server server = this.resourceService.spinServer();
        this.resourceService.serverSpinnerMap.put(server,this);
        Thread.sleep(20000);
        server.setServerState(ServerState.ACTIVE);
        this.serverRepository.save(server);

    }
}
