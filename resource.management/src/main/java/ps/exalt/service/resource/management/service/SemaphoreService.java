package ps.exalt.service.resource.management.service;

import org.springframework.stereotype.Service;

public interface SemaphoreService {
    void acquire() throws InterruptedException;

    void release();
}
