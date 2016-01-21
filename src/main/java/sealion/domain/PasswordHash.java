package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class PasswordHash {

    private final String value;

    public PasswordHash(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
