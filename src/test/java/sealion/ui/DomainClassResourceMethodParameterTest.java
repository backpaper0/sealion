package sealion.ui;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import sealion.domain.EmailAddress;
import sealion.domain.MarkedText;
import sealion.domain.MilestoneName;
import sealion.domain.ProjectName;
import sealion.domain.TaskTitle;
import sealion.domain.Username;

@RunWith(Parameterized.class)
public class DomainClassResourceMethodParameterTest extends JerseyTest {

    @Parameter(0)
    public String domainClass;

    @Parameters(name = "{0}")
    public static List<String> parameters() {
        Stream.Builder<Class<?>> ps = Stream.builder();
        ps.add(TaskTitle.class);
        ps.add(Username.class);
        ps.add(MilestoneName.class);
        ps.add(ProjectName.class);
        ps.add(MarkedText.class);
        ps.add(EmailAddress.class);
        return ps.build().map(c -> c.getSimpleName()).collect(Collectors.toList());
    }

    @Test
    public void string_to_domain_class() throws Exception {
        String response = target(domainClass).queryParam("q", "x").request().get(String.class);
        assertThat(response).isEqualTo(domainClass + ":x");
    }

    @Test
    public void empty_string() throws Exception {
        String response = target(domainClass).queryParam("q", "").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void null_string() throws Exception {
        String response = target(domainClass).request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(TestResource.class);
    }

    @Path("")
    public static class TestResource {

        @Path("TaskTitle")
        @GET
        public String passthrough(@QueryParam("q") TaskTitle value) {
            return Optional.ofNullable(value)
                    .map(a -> a.getClass().getSimpleName() + ":" + a.getValue()).orElse("empty");
        }

        @Path("Username")
        @GET
        public String passthrough(@QueryParam("q") Username value) {
            return Optional.ofNullable(value)
                    .map(a -> a.getClass().getSimpleName() + ":" + a.getValue()).orElse("empty");
        }

        @Path("MilestoneName")
        @GET
        public String passthrough(@QueryParam("q") MilestoneName value) {
            return Optional.ofNullable(value)
                    .map(a -> a.getClass().getSimpleName() + ":" + a.getValue()).orElse("empty");
        }

        @Path("ProjectName")
        @GET
        public String passthrough(@QueryParam("q") ProjectName value) {
            return Optional.ofNullable(value)
                    .map(a -> a.getClass().getSimpleName() + ":" + a.getValue()).orElse("empty");
        }

        @Path("MarkedText")
        @GET
        public String passthrough(@QueryParam("q") MarkedText value) {
            return Optional.ofNullable(value)
                    .map(a -> a.getClass().getSimpleName() + ":" + a.getValue()).orElse("empty");
        }

        @Path("EmailAddress")
        @GET
        public String passthrough(@QueryParam("q") EmailAddress value) {
            return Optional.ofNullable(value)
                    .map(a -> a.getClass().getSimpleName() + ":" + a.getValue()).orElse("empty");
        }
    }
}
