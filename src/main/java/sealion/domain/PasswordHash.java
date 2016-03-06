package sealion.domain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class PasswordHash {

    private final String value;

    public PasswordHash(String value) {
        Objects.requireNonNull(value);
        if (value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PasswordHash hash(String password, Salt salt) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        byte[] input = (password + salt.getValue()).getBytes(StandardCharsets.UTF_8);
        byte[] digest = md.digest(input);
        String value = IntStream.range(0, digest.length)
                .mapToObj(i -> String.format("%02x", digest[i] & 0xff))
                .collect(Collectors.joining());
        return new PasswordHash(value);
    }
}
