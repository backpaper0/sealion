package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import sealion.domain.Key;

@Entity
public class Password {
    @Id
    public Key<Account> account;
    public String hash;
    public String salt;
    public String hashAlgorithm;
}
