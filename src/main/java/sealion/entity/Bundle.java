package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import sealion.domain.Key;

@Entity
public class Bundle {
    @Id
    public Key<Task> task;
    public Key<Milestone> milestone;
}
