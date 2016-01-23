package sealion.domain;

import java.util.Optional;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class Username {

    private final String value;

    public Username(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Username valueOf(String value) {
        return Optional.ofNullable(value).filter(a -> a.isEmpty() == false).map(Username::new)
                .orElse(null);
    }
}
