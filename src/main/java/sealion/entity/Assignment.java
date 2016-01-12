package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import sealion.domain.Key;

@Entity
public class Assignment {
    @Id
    public Key<Task> task;
    @Id
    public Key<Account> account;
}
