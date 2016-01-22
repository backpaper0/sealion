package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import sealion.domain.FixedDate;
import sealion.domain.Key;
import sealion.domain.MilestoneName;

@Entity
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key<Milestone> id;
    public MilestoneName name;
    public FixedDate fixedDate;
    public Key<Project> project;
}
