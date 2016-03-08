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
import sealion.domain.MarkedText;
import sealion.entity.Comment;
import sealion.entity.Task;
import sealion.service.CommentService;
import sealion.test.Always200OK;

@RunWith(Enclosed.class)
public class CommentResourceTest {
    public static class RoutingTest extends JerseyTest {

        @Test
        public void redirect_after_created() throws Exception {
            Form form = new Form();
            form.param("content", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/tasks/2/comments/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.SEE_OTHER.getStatusCode());
            assertThat(response.getLocation())
                    .isEqualTo(getBaseUri().resolve("/projects/1/tasks/2"));
        }

        @Override
        protected void configureClient(ClientConfig config) {
            config.property(ClientProperties.FOLLOW_REDIRECTS, false);
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(CommentResource.class).register(new Dependencies())
                    .register(TaskResource.class);
        }
    }

    public static class ValidationTest extends JerseyTest {

        @Test
        public void validate() throws Exception {
            Form form = new Form();
            form.param("content", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/tasks/2/comments/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_content_null() throws Exception {
            Form form = new Form();
            //form.param("content", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/tasks/2/comments/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_content_empty() throws Exception {
            Form form = new Form();
            form.param("content", "");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/tasks/2/comments/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_content_max_length() throws Exception {
            Form form = new Form();
            form.param("content",
                    IntStream.range(0, 500).mapToObj(a -> "x").collect(Collectors.joining()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/tasks/2/comments/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_content_over_max_length() throws Exception {
            Form form = new Form();
            form.param("content",
                    IntStream.range(0, 501).mapToObj(a -> "x").collect(Collectors.joining()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/tasks/2/comments/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(CommentResource.class).register(new Always200OK())
                    .register(new Dependencies());
        }
    }

    private static class Dependencies extends AbstractBinder {
        @Override
        protected void configure() {
            bind(new CommentService() {
                @Override
                public Comment create(Key<Task> task, MarkedText content) {
                    Comment comment = new Comment();
                    comment.id = new Key<>(3L);
                    return comment;
                }
            }).to(CommentService.class);
        }
    }
}