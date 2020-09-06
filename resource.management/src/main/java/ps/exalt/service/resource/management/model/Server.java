package ps.exalt.service.resource.management.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ps.exalt.service.resource.management.enums.ServerState;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Server {

    public Server(Long capacity) {
        this.capacity = capacity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long capacity;
    private Long free;
    private ServerState serverState;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date lastModified;

}
