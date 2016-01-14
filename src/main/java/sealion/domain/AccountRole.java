package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = String.class, factoryMethod = "of")
public enum AccountRole {

    USER, ADMIN;

    public String getValue() {
        return name();
    }

    public static AccountRole of(String value) {
        return valueOf(value);
    }
}
