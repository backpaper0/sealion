package sealion.domain;

import java.util.Objects;
import java.util.Optional;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class ProjectName {

    private final String value;

    public ProjectName(String value) {
        Objects.requireNonNull(value);
        if (value.isEmpty() || value.length() > 50) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static ProjectName valueOf(String value) {
        return Optional.ofNullable(value).filter(a -> a.isEmpty() == false).map(ProjectName::new)
                .orElse(null);
    }
}
