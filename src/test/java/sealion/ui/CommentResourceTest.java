package sealion.ui;

import static org.assertj.core.api.Assertions.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.entity.Comment;
import sealion.entity.Task;
import sealion.service.CommentService;

public class CommentResourceTest extends JerseyTest {

    @Test
    public void redirect_after_created() throws Exception {
        Form form = new Form();
        form.param("content", "x");
        Entity<Form> entity = Entity.form(form);
        Response response = target("/projects/1/tasks/2/comments/new").request().post(entity);
        assertThat(response.getStatus()).isEqualTo(Status.SEE_OTHER.getStatusCode());
        assertThat(response.getLocation()).isEqualTo(getBaseUri().resolve("/projects/1/tasks/2"));
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.property(ClientProperties.FOLLOW_REDIRECTS, false);
    }

    @Override
    protected Application configure() {
        Binder binder = new AbstractBinder() {

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
        };
        return new ResourceConfig().register(CommentResource.class).register(binder)
                .register(TaskResource.class);
    }
}
