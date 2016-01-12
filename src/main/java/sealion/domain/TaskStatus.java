package sealion.domain;

import org.seasar.doma.Domain;

@Domain(valueType = String.class, factoryMethod = "of")
public enum TaskStatus {

    OPENED, DOING, DONE, CLOSED;

    public String getValue() {
        return name();
    }

    public static TaskStatus of(String value) {
        return valueOf(value);
    }
}
