package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = String.class, factoryMethod = "of")
public enum Role {

    USER, ADMIN;

    public String getValue() {
        return name();
    }

    public static Role of(String value) {
        return valueOf(value);
    }
}
