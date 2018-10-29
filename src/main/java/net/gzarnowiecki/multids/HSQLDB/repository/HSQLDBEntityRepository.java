package net.gzarnowiecki.multids.HSQLDB.repository;

import net.gzarnowiecki.multids.HSQLDB.domain.HSQLDBEntity;
import org.springframework.data.repository.CrudRepository;

public interface HSQLDBEntityRepository extends CrudRepository<HSQLDBEntity, Long> {
}
