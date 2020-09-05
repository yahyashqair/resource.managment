package ps.exalt.service.resource.management.service;

import ps.exalt.service.resource.management.aop.annotation.LockThread;
import ps.exalt.service.resource.management.model.Server;

public interface ResourceService {

    @LockThread
    void allocateِSpace(Long numberOfGiga);

    Server spinServer();

    void getClientStateus(Long ClientId);
}
