package sealion.domain;

import java.util.Objects;

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
}
