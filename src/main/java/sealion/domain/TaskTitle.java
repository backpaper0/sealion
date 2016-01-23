package sealion.domain;

import java.util.Optional;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class TaskTitle {

    private final String value;

    public TaskTitle(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static TaskTitle valueOf(String value) {
        return Optional.ofNullable(value).filter(a -> a.isEmpty() == false).map(TaskTitle::new)
                .orElse(null);
    }
}
