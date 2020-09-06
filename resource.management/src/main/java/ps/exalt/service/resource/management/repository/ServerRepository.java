package ps.exalt.service.resource.management.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import ps.exalt.service.resource.management.enums.ServerState;
import ps.exalt.service.resource.management.model.Server;

import java.util.List;

public interface ServerRepository extends JpaRepository<Server,Long> {
    List<Server> findAllByServerState(ServerState serverState);
}
