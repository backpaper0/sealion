package sealion.domain;

import java.util.Objects;

import org.seasar.doma.Domain;

@Domain(valueType = Long.class)
public class Key<ENTITY> {

    private final Long value;

    public Key(Long value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }
        Key<?> other = getClass().cast(obj);
        if (value.equals(other.value) == false) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /*
     * このメソッドはJAX-RSのパラメーターでKeyクラスを使用するために定義しています。
     * 
     * JAX-RSではString型の引数を1つだけもつvalueOfという名前のstaticファクトリーメソッドが
     * あるクラスを@QueryParamや@FormParamで受け取るパラーメーターとして使えます。
     * 
     * 詳しくは次のURLを参照してください。
     * http://backpaper0.github.io/2013/07/17/jaxrs_parameter.html
     */
    public static <ENTITY> Key<ENTITY> valueOf(String value) {
        return new Key<>(Long.valueOf(value));
    }
}
