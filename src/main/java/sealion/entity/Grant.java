package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import sealion.domain.Key;
import sealion.domain.Role;

@Entity
public class Grant {
    @Id
    public Key<Account> account;
    public Role role;
}
