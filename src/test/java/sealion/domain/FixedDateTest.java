package sealion.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.Test;

public class FixedDateTest {

    @Test
    public void valueOf() throws Exception {
        FixedDate fixedDate = FixedDate.valueOf("2016-01-22");
        assertThat(fixedDate.getValue()).isEqualTo(LocalDate.of(2016, 1, 22));
    }
}
