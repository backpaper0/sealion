package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class MarkedText {

    private final String value;

    public MarkedText(String value) {
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
