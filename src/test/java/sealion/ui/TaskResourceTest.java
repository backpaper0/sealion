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
import sealion.domain.TaskTitle;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.model.NewTaskModel;
import sealion.model.TaskModel;
import sealion.model.TasksModel;
import sealion.service.TaskService;

public class TaskResourceTest extends JerseyTest {

    @Test
    public void redirect_after_created() throws Exception {
        Form form = new Form();
        form.param("title", "x");
        form.param("content", "x");
        Entity<Form> entity = Entity.form(form);
        Response response = target("/projects/1/tasks/new").request().post(entity);
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
                bind(new TasksModel.Builder()).to(TasksModel.Builder.class);
                bind(new NewTaskModel.Builder()).to(NewTaskModel.Builder.class);
                bind(new TaskModel.Builder()).to(TaskModel.Builder.class);
                bind(new TaskService() {
                    @Override
                    public Task create(Key<Project> project, TaskTitle title, MarkedText content) {
                        Task task = new Task();
                        task.id = new Key<>(2L);
                        return task;
                    }
                }).to(TaskService.class);
            }
        };
        return new ResourceConfig().register(TaskResource.class).register(binder);
    }
}
