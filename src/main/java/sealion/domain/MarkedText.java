package sealion.domain;

import java.util.Objects;
import java.util.Optional;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class MarkedText {

    private final String value;

    public MarkedText(String value) {
        Objects.requireNonNull(value);
        if (value.isEmpty() || value.length() > 500) {
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

    public static MarkedText valueOf(String value) {
        return Optional.ofNullable(value).filter(a -> a.isEmpty() == false).map(MarkedText::new)
                .orElse(null);
    }
}
