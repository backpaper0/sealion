package sealion.domain;

import java.util.Objects;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class Username {

    private final String value;

    public Username(String value) {
        Objects.requireNonNull(value);
        if (value.isEmpty() || value.length() > 20) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
