package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = Integer.class, factoryMethod = "of")
public class Key<ENTITY> {

    private final Integer value;

    private Key(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static <ENTITY> Key<ENTITY> of(Integer value) {
        return new Key<>(value);
    }
}
