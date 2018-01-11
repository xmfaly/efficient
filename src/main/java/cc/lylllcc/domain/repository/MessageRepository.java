package cc.lylllcc.domain.repository;

import cc.lylllcc.domain.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MessageRepository extends CrudRepository<Message,Integer>{
    Message findFirstByUsername(String username);
    Iterable<Message> findByUsername(String username);
    Collection<Message> findByUsernameAndCode(String username, String code);
}
