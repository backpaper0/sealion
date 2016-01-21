package sealion.domain;

import java.time.LocalDateTime;

import org.seasar.doma.Domain;

@Domain(valueType = LocalDateTime.class)
public class PostedDate {

    private final LocalDateTime value;

    public PostedDate(LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
