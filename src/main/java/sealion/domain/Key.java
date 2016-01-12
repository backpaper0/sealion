package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = Long.class, factoryMethod = "of")
public class Key<ENTITY> {

    private final Long value;

    private Key(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static <ENTITY> Key<ENTITY> of(Long value) {
        return new Key<>(value);
    }
}
