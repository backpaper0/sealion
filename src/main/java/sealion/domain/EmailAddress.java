package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class EmailAddress {

    private final String value;

    public EmailAddress(String value) {
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
