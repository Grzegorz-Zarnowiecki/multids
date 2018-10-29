package net.gzarnowiecki.multids.H2.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class H2Entity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long Id;
    private String Description;
}
