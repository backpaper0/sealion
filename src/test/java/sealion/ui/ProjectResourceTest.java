package sealion.ui;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import sealion.domain.Key;
import sealion.domain.ProjectName;
import sealion.entity.Project;
import sealion.model.ProjectsModel;
import sealion.service.ProjectService;
import sealion.test.Always200OK;

@RunWith(Enclosed.class)
public class ProjectResourceTest {

    public static class RoutingTest extends JerseyTest {

        @Test
        public void redirect_after_created() throws Exception {
            Form form = new Form();
            form.param("name", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.SEE_OTHER.getStatusCode());
            assertThat(response.getLocation()).isEqualTo(getBaseUri().resolve("/projects/1"));
        }

        @Override
        protected void configureClient(ClientConfig config) {
            config.property(ClientProperties.FOLLOW_REDIRECTS, false);
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(ProjectResource.class)
                    .register(new Dependencies());
        }
    }

    public static class ValidationTest extends JerseyTest {

        @Test
        public void validate() throws Exception {
            Form form = new Form();
            form.param("name", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_name_null() throws Exception {
            Form form = new Form();
            //form.param("name", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_name_empty() throws Exception {
            Form form = new Form();
            form.param("name", "");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_name_max_length() throws Exception {
            Form form = new Form();
            form.param("name",
                    IntStream.range(0, 50).mapToObj(a -> "x").collect(Collectors.joining()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_name_over_max_length() throws Exception {
            Form form = new Form();
            form.param("name",
                    IntStream.range(0, 51).mapToObj(a -> "x").collect(Collectors.joining()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(ProjectResource.class).register(new Always200OK())
                    .register(new Dependencies());
        }
    }

    private static class Dependencies extends AbstractBinder {
        @Override
        protected void configure() {
            bind(new ProjectsModel.Builder()).to(ProjectsModel.Builder.class);
            bind(new ProjectService() {
                @Override
                public Project create(ProjectName name) {
                    Project project = new Project();
                    project.id = new Key<>(1L);
                    return project;
                }
            }).to(ProjectService.class);
        }
    }
}
