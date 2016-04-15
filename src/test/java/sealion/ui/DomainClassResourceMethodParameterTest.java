package sealion.ui;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import sealion.domain.EmailAddress;
import sealion.domain.MarkedText;
import sealion.domain.MilestoneName;
import sealion.domain.ProjectName;
import sealion.domain.TaskTitle;
import sealion.domain.Username;

public class DomainClassResourceMethodParameterTest extends JerseyTest {

    @Test
    public void string_to_TaskTitle() throws Exception {
        String response = target("TaskTitle").queryParam("q", "x").request().get(String.class);
        assertThat(response).isEqualTo("TaskTitle:x");
    }

    @Test
    public void empty_string_to_TaskTitle() throws Exception {
        String response = target("TaskTitle").queryParam("q", "").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void null_to_TaskTitle() throws Exception {
        String response = target("TaskTitle").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void string_to_Username() throws Exception {
        String response = target("Username").queryParam("q", "x").request().get(String.class);
        assertThat(response).isEqualTo("Username:x");
    }

    @Test
    public void empty_string_to_Username() throws Exception {
        String response = target("Username").queryParam("q", "").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void null_to_Username() throws Exception {
        String response = target("Username").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void string_to_MilestoneName() throws Exception {
        String response = target("MilestoneName").queryParam("q", "x").request().get(String.class);
        assertThat(response).isEqualTo("MilestoneName:x");
    }

    @Test
    public void empty_string_to_MilestoneName() throws Exception {
        String response = target("MilestoneName").queryParam("q", "").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void null_to_MilestoneName() throws Exception {
        String response = target("MilestoneName").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void string_to_ProjectName() throws Exception {
        String response = target("ProjectName").queryParam("q", "x").request().get(String.class);
        assertThat(response).isEqualTo("ProjectName:x");
    }

    @Test
    public void empty_string_to_ProjectName() throws Exception {
        String response = target("ProjectName").queryParam("q", "").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void null_to_ProjectName() throws Exception {
        String response = target("ProjectName").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void string_to_MarkedText() throws Exception {
        String response = target("MarkedText").queryParam("q", "x").request().get(String.class);
        assertThat(response).isEqualTo("MarkedText:x");
    }

    @Test
    public void empty_string_to_MarkedText() throws Exception {
        String response = target("MarkedText").queryParam("q", "").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void null_to_MarkedText() throws Exception {
        String response = target("MarkedText").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void string_to_EmailAddress() throws Exception {
        String response = target("EmailAddress").queryParam("q", "x").request().get(String.class);
        assertThat(response).isEqualTo("EmailAddress:x");
    }

    @Test
    public void empty_string_to_EmailAddress() throws Exception {
        String response = target("EmailAddress").queryParam("q", "").request().get(String.class);
        assertThat(response).isEqualTo("empty");
    }

    @Test
    public void null_to_EmailAddress() throws Exception {
        String response = target("EmailAddress").request().get(String.class);
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
