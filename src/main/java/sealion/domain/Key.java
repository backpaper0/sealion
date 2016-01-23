package sealion.domain;

import java.util.Objects;

import org.seasar.doma.Domain;

@Domain(valueType = Long.class)
public class Key<ENTITY> {

    private final Long value;

    public Key(Long value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static <ENTITY> Key<ENTITY> valueOf(String value) {
        return new Key<>(Long.valueOf(value));
    }
}
