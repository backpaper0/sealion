package sealion.domain;

import java.util.Optional;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class MilestoneName {

    private final String value;

    public MilestoneName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static MilestoneName valueOf(String value) {
        return Optional.ofNullable(value).filter(a -> a.isEmpty() == false).map(MilestoneName::new)
                .orElse(null);
    }
}
