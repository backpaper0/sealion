package sealion.entity;

import java.time.LocalDate;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import sealion.domain.Key;

@Entity
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key<Milestone> id;
    public String name;
    public LocalDate fixedDate;
}
