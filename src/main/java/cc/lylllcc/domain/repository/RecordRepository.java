package cc.lylllcc.domain.repository;

import cc.lylllcc.domain.model.Record;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lylllcc on 2017/2/25.
 */
public interface RecordRepository extends CrudRepository<Record,Integer>{

    Iterable<Record> findByUsername(String username);
}
