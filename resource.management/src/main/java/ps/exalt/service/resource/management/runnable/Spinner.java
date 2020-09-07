package ps.exalt.service.resource.management.runnable;

import lombok.Data;
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
@Data
public class Spinner extends Thread{

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ServerRepository serverRepository;

    private Long initialReserve = null;

    @SneakyThrows
    @Override
    public void run() {
        Server server = this.resourceService.spinServer();
        if(initialReserve!=null){
            server.setFree(server.getFree() - initialReserve);
            this.serverRepository.save(server);
        }
        this.resourceService.serverSpinnerMap.put(server.getId(),this);
        synchronized (this) {
            this.wait(20000);
        }

        server.setServerState(ServerState.ACTIVE);
        this.serverRepository.save(server);
        this.resourceService.serverSpinnerMap.remove(server.getId());
    }
}
