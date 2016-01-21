package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class Salt {

    private final String value;

    public Salt(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
