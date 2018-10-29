package net.gzarnowiecki.multids.H2.repository;

import net.gzarnowiecki.multids.H2.domain.H2Entity;
import org.springframework.data.repository.CrudRepository;

public interface H2EntityRepository extends CrudRepository<H2Entity, Long> {
}
