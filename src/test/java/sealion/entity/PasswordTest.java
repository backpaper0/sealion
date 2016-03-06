package sealion.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import sealion.domain.HashAlgorithm;
import sealion.domain.PasswordHash;
import sealion.domain.Salt;

public class PasswordTest {

    @Test
    public void test() throws Exception {
        Password p = new Password();
        p.hash = PasswordHash.hash("secret", p.salt = Salt.generate());
        p.hashAlgorithm = HashAlgorithm.SHA512;

        assertThat(p.test("secret")).isTrue();
        assertThat(p.test("password")).isFalse();
    }
}
