package ps.exalt.service.resource.management.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ps.exalt.service.resource.management.aop.annotation.LockThread;
import ps.exalt.service.resource.management.aop.annotation.TimeTracking;
import ps.exalt.service.resource.management.model.Server;
import ps.exalt.service.resource.management.runnable.Spinner;

import java.util.HashMap;
import java.util.Map;

@Service
public interface ResourceService {

    HashMap<Long, Spinner> serverSpinnerMap = new HashMap<>();

    Map<String,String> reserveSpace(Long numberOfGiga) throws InterruptedException;

    @LockThread
    Server allocateActiveServer(Long numberOfGiga);

    @LockThread
    public Server allocateCreatingServer(Long numberOfGiga);

    Server spinServer() throws InterruptedException;

    void getClientStateus(Long ClientId);
}
