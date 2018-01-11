package cc.lylllcc.domain.repository;

import cc.lylllcc.domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by lylllcc on 2017/2/23.
 */

public interface UserRepository extends CrudRepository<User,Integer>{

    Collection<User> findByUsername(String username);
    Collection<User> findByPhone(String phone);
    User findFirstByUsername(String username);
    User findFirstByPhone(String phone);
}
