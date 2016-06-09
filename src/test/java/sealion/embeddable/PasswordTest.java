package sealion.embeddable;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import sealion.domain.HashAlgorithm;
import sealion.domain.PasswordHash;
import sealion.domain.Salt;

public class PasswordTest {

    @Test
    public void test() throws Exception {
        Salt salt = Salt.generate();
        PasswordHash hash = PasswordHash.hash("secret", salt);
        HashAlgorithm hashAlgorithm = HashAlgorithm.SHA512;
        Password p = new Password(hash, salt, hashAlgorithm);

        assertThat(p.test("secret")).isTrue();
        assertThat(p.test("password")).isFalse();
    }
}
