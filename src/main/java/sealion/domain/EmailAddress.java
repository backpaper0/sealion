package sealion.domain;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class EmailAddress {

    private final String value;

    public EmailAddress(String value) {
        Objects.requireNonNull(value);
        if (value.isEmpty() || value.length() > 100) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public byte[] asBytes() {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
