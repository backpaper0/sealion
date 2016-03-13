package sealion.config;

import java.time.Clock;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ClockProvider {

    @Produces
    @ApplicationScoped
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
