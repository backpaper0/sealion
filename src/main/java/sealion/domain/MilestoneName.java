package sealion.domain;

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
}
