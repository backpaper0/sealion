package sealion.ui;

import static org.assertj.core.api.Assertions.*;

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

import sealion.domain.FixedDate;
import sealion.domain.Key;
import sealion.domain.MilestoneName;
import sealion.entity.Milestone;
import sealion.entity.Project;
import sealion.model.MilestoneModel;
import sealion.model.MilestonesModel;
import sealion.model.NewMilestoneModel;
import sealion.service.MilestoneService;
import sealion.test.Always200OK;

@RunWith(Enclosed.class)
public class MilestoneResourceTest {

    public static class RoutingTest extends JerseyTest {

        @Test
        public void redirect_after_created() throws Exception {
            Form form = new Form();
            form.param("name", "x");
            form.param("fixedDate", "2016-01-22");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/milestones/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.SEE_OTHER.getStatusCode());
            assertThat(response.getLocation())
                    .isEqualTo(getBaseUri().resolve("/projects/1/milestones/2"));
        }

        @Override
        protected void configureClient(ClientConfig config) {
            config.property(ClientProperties.FOLLOW_REDIRECTS, false);
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(MilestoneResource.class)
                    .register(new Dependencies());
        }
    }

    public static class ValidationTest extends JerseyTest {

        @Test
        public void validate_name() throws Exception {
            Form form = new Form();
            form.param("name", "x");
            form.param("fixedDate", "2016-01-22");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/milestones/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_name_null() throws Exception {
            Form form = new Form();
            //form.param("name", "x");
            form.param("fixedDate", "2016-01-22");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/milestones/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_name_empty() throws Exception {
            Form form = new Form();
            form.param("name", "");
            form.param("fixedDate", "2016-01-22");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/milestones/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_fixedDate_null() throws Exception {
            Form form = new Form();
            form.param("name", "x");
            //form.param("fixedDate", "2016-01-22");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/milestones/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_fixedDate_empty() throws Exception {
            Form form = new Form();
            form.param("name", "x");
            form.param("fixedDate", "");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/projects/1/milestones/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(MilestoneResource.class)
                    .register(new Always200OK()).register(new Dependencies());
        }
    }

    private static class Dependencies extends AbstractBinder {
        @Override
        protected void configure() {
            bind(new MilestonesModel.Builder()).to(MilestonesModel.Builder.class);
            bind(new NewMilestoneModel.Builder()).to(NewMilestoneModel.Builder.class);
            bind(new MilestoneModel.Builder()).to(MilestoneModel.Builder.class);
            bind(new MilestoneService() {
                @Override
                public Milestone create(Key<Project> project, MilestoneName name,
                        FixedDate fixedDate) {
                    Milestone milestone = new Milestone();
                    milestone.id = new Key<>(2L);
                    return milestone;
                }
            }).to(MilestoneService.class);
        }
    }
}
