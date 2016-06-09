package sealion.embeddable;

import org.seasar.doma.Embeddable;

import sealion.domain.HashAlgorithm;
import sealion.domain.PasswordHash;
import sealion.domain.Salt;

@Embeddable
public class Password {
    private final PasswordHash hash;
    private final Salt salt;
    private final HashAlgorithm hashAlgorithm;

    public Password(PasswordHash hash, Salt salt, HashAlgorithm hashAlgorithm) {
        this.hash = hash;
        this.salt = salt;
        this.hashAlgorithm = hashAlgorithm;
    }

    public boolean test(String password) {
        switch (hashAlgorithm) {
        case PLAIN:
            return hash.getValue().equals(password);
        case SHA512:
            return hash.is(PasswordHash.hash(password, salt));
        default:
            throw new RuntimeException();
        }
    }
}
