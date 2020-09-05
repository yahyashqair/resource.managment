package ps.exalt.service.resource.management.service.impl;

import org.springframework.stereotype.Service;
import ps.exalt.service.resource.management.service.SemaphoreService;

import java.util.concurrent.Semaphore;

@Service
public class SemaphoreServiceImpl implements SemaphoreService {

    private Semaphore semaphore = new Semaphore(1);

    @Override
    public void acquire() throws InterruptedException {
        this.semaphore.acquire();
    }

    @Override
    public void release() {
        this.semaphore.release();
    }
}
