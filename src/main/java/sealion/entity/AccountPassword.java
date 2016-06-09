package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import sealion.domain.Key;
import sealion.embeddable.Password;

@Entity
public class AccountPassword {
    @Id
    public Key<Account> account;
    public Password password;
}
