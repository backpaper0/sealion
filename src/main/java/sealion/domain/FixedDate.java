package sealion.domain;

import java.time.LocalDate;
import java.util.Objects;

import org.seasar.doma.Domain;

@Domain(valueType = LocalDate.class)
public class FixedDate {

    private final LocalDate value;

    public FixedDate(LocalDate value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /*
     * KeyのvalueOfメソッドと同じくJAX-RSのパラメーターで
     * 使いたいから定義しています。
     * 詳しくはKeyのvalueOfメソッドのコメントを参照してください。
     * 
     */
    public static FixedDate valueOf(String text) {
        return new FixedDate(LocalDate.parse(text));
    }
}
