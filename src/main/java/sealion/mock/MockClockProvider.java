package sealion.mock;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class MockClockProvider {

    @Produces
    @ApplicationScoped
    public Clock clock() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime dt = ZonedDateTime.of(2016, 2, 1, 15, 0, 0, 0, zone);
        Instant fixedInstant = dt.toInstant();
        return Clock.fixed(fixedInstant, zone);
    }
}
