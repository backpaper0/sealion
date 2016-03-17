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

    public boolean test(String password) {
        switch (hashAlgorithm) {
        case PLAIN:
            return hash.getValue().equals(password);
        case SHA512:
            return hash.getValue().equals(PasswordHash.hash(password, salt).getValue());
        default:
            throw new RuntimeException();
        }
    }
}
