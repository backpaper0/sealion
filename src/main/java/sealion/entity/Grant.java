package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import sealion.domain.Key;
import sealion.domain.AccountRole;

@Entity
public class Grant {
    @Id
    public Key<Account> account;
    public AccountRole role;
}
