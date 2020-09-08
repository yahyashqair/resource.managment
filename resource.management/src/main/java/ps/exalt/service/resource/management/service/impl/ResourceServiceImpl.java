package ps.exalt.service.resource.management.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ps.exalt.service.resource.management.aop.annotation.Synchronized;
import ps.exalt.service.resource.management.aop.annotation.TimeTracking;
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

    @Autowired
    private ResourceService resourceService;

    @TimeTracking
    @Override
    public Map<String, String> reserveSpace(Long numberOfGiga) throws InterruptedException {
        Map<String, String> result = new HashMap<>();
        // try to allocate in active servers
        Server server = this.resourceService.allocateActiveServer(numberOfGiga);
        if(server != null){
            result.put("STATE","DONE");
            return result;
        }
        Spinner spinner = this.resourceService.allocateCreatingServer(numberOfGiga);
        System.out.println("I am waiting"+ Thread.currentThread().getName());
        if(spinner != null){
                while (spinner.isAlive()) {
                    try {
                        spinner.join();
                    } catch (InterruptedException e) {
                        System.err.println("error in join a thread");
                    }
                }
            System.out.println("oh no, i am finished" + Thread.currentThread().getName());
            result.put("STATE","DONE");
            return result;
        }
        return null;
    }

    @Synchronized
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

    @SneakyThrows
    @Synchronized
    @Override
    public Spinner allocateCreatingServer(Long numberOfGiga) {
        List<Server> serverList = this.serverRepository.findAllByServerState(ServerState.CREATING);
        for (Server server : serverList) {
            if (server.getFree() >= numberOfGiga) {
                server.setFree(server.getFree() - numberOfGiga);
                server = this.serverRepository.save(server);
                return this.resourceService.serverSpinnerMap.get(server.getId());
            }
        }
        // spin a server and join it ..
        Server server = this.resourceService.spinServer(numberOfGiga);
        Spinner spinner = this.applicationContext.getBean(Spinner.class);
        this.resourceService.serverSpinnerMap.put(server.getId(),spinner);
        spinner.setServerId(server.getId());
        spinner.start();
        return spinner;
    }

    @Override
    @Synchronized
    public void serverIsReady(Long serverId) throws InterruptedException {
        Server server = this.serverRepository.findById(serverId).get();
        server.setServerState(ServerState.ACTIVE);
        this.serverRepository.save(server);
    }


    @Override
    public Server spinServer(long initialReserve) throws InterruptedException {
        Server server = new Server();
        server.setFree(100L - initialReserve);
        server.setCapacity(100L);
        server.setServerState(ServerState.CREATING);
        server = this.serverRepository.save(server);
        return server;
    }

}
