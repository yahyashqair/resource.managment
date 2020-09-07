package ps.exalt.service.resource.management.service;

import ps.exalt.service.resource.management.aop.annotation.Synchronized;
import ps.exalt.service.resource.management.model.Server;
import ps.exalt.service.resource.management.runnable.Spinner;

import java.util.HashMap;
import java.util.Map;
@Synchronized
public interface ResourceService {

    HashMap<Long, Spinner> serverSpinnerMap = new HashMap<>();

    Map<String,String> reserveSpace(Long numberOfGiga) throws InterruptedException;

    Server allocateActiveServer(Long numberOfGiga);

    public Server allocateCreatingServer(Long numberOfGiga);

    Server spinServer() throws InterruptedException;

}
