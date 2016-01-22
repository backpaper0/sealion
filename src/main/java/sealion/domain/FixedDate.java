package sealion.domain;

import java.time.LocalDate;

import org.seasar.doma.Domain;

@Domain(valueType = LocalDate.class)
public class FixedDate {

    private final LocalDate value;

    public FixedDate(LocalDate value) {
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static FixedDate valueOf(String text) {
        return new FixedDate(LocalDate.parse(text));
    }
}
