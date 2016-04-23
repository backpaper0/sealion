package sealion.domain;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class Salt {

    private final String value;

    public Salt(String value) {
        Objects.requireNonNull(value);
        if (value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public byte[] join(String password) {
        return (password + value).getBytes(StandardCharsets.UTF_8);
    }

    public static Salt generate() {
        byte[] bs = new byte[64];
        try {
            SecureRandom.getInstance("NativePRNGNonBlocking").nextBytes(bs);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String value = IntStream.range(0, bs.length)
                .mapToObj(i -> String.format("%02x", bs[i] & 0xff)).collect(Collectors.joining());
        return new Salt(value);
    }
}
