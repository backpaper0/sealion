package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import sealion.domain.HashAlgorithm;
import sealion.domain.Key;
import sealion.domain.PasswordHash;
import sealion.domain.Salt;

@Entity
public class Password {
    @Id
    public Key<Account> account;
    public PasswordHash hash;
    public Salt salt;
    public HashAlgorithm hashAlgorithm;
}
