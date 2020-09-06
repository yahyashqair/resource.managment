package ps.exalt.service.resource.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ps.exalt.service.resource.management.enums.ServerState;
import ps.exalt.service.resource.management.model.Server;
import ps.exalt.service.resource.management.repository.ServerRepository;
import ps.exalt.service.resource.management.runnable.Spinner;
import ps.exalt.service.resource.management.service.ResourceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Map<String, String> reserveSpace(Long numberOfGiga) throws InterruptedException {
        Map<String, String> result = new HashMap<>();
        // try to allocate in active servers
        Server server = allocateActiveServer(numberOfGiga);
        if(server != null){
            result.put("STATE","DONE");
            result.put("SERVER","DONE");
            return result;
        }
        server = allocateCreatingServer(numberOfGiga);
        if(server != null){
            Spinner spinner= this.serverSpinnerMap.get(server);
            spinner.wait();
            result.put("STATE","DONE");
            result.put("SERVER","DONE");
            return result;
        }
        // spin a server and join it ..
        Spinner spinner = this.applicationContext.getBean(Spinner.class);
        spinner.run();
        spinner.wait();

        return null;
    }

    @Override
    public Server allocateActiveServer(Long numberOfGiga) {
        // get all servers
        List<Server> serverList = this.serverRepository.findAllByServerState(ServerState.ACTIVE);
        // search for one that has enough space,
        for (Server server : serverList) {
            if (server.getFree() >= numberOfGiga) {
                server.setFree(server.getFree() - numberOfGiga);
                server = this.serverRepository.save(server);
                return server;
            }
        }
        return null;
    }

    @Override
    public Server allocateCreatingServer(Long numberOfGiga) {
        List<Server> serverList = this.serverRepository.findAllByServerState(ServerState.CREATING);
        for (Server server : serverList) {
            if (server.getFree() >= numberOfGiga) {
                server.setFree(server.getFree() - numberOfGiga);
                server = this.serverRepository.save(server);
                return server;
            }
        }
        return null;
    }

    @Override
    public Server spinServer() throws InterruptedException {
        Server server = new Server();
        server.setFree(100L);
        server.setCapacity(100L);
        server.setServerState(ServerState.CREATING);
        server = this.serverRepository.save(server);
        return server;
    }

    @Override
    public void getClientStateus(Long ClientId) {

    }
}
