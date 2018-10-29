package net.gzarnowiecki.multids;

import net.gzarnowiecki.multids.H2.domain.H2Entity;
import net.gzarnowiecki.multids.H2.repository.H2EntityRepository;
import net.gzarnowiecki.multids.HSQLDB.domain.HSQLDBEntity;
import net.gzarnowiecki.multids.HSQLDB.repository.HSQLDBEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Initializer {
    final
    H2EntityRepository h2EntityRepository;
    final
    HSQLDBEntityRepository hsqldbEntityRepository;

    @Autowired
    public Initializer(H2EntityRepository h2EntityRepository, HSQLDBEntityRepository hsqldbEntityRepository) {
        this.h2EntityRepository = h2EntityRepository;
        this.hsqldbEntityRepository = hsqldbEntityRepository;
    }

    @PostConstruct
    public void init() {
        H2Entity h2entity = new H2Entity();
        h2entity.setDescription("New Entity");
        HSQLDBEntity hsqldbEntity = new HSQLDBEntity();
        hsqldbEntity.setDescription("New Entity HsqlDB");
        hsqldbEntity.setValue(100);
        h2EntityRepository.save(h2entity);
        hsqldbEntityRepository.save(hsqldbEntity);

        if (hsqldbEntityRepository.count() == 1) {
            System.out.println("HSQLDB OK");
        }
        if (h2EntityRepository.count() == 1) {
            System.out.println("H2DB OK");
        }


    }
}
