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

import sealion.domain.EmailAddress;
import sealion.service.SecurityService;
import sealion.session.UserProvider;
import sealion.test.Always200OK;

@RunWith(Enclosed.class)
public class SigninResourceTest {

    public static class RoutingTest extends JerseyTest {

        @Test
        public void redirect_after_signin() throws Exception {
            Form form = new Form();
            form.param("email", "x");
            form.param("password", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/signin").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.SEE_OTHER.getStatusCode());
            assertThat(response.getLocation()).isEqualTo(getBaseUri().resolve("/projects"));
        }

        @Override
        protected void configureClient(ClientConfig config) {
            config.property(ClientProperties.FOLLOW_REDIRECTS, false);
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(SigninResource.class).register(new Dependencies());
        }
    }

    public static class ValidationTest extends JerseyTest {

        @Test
        public void validate() throws Exception {
            Form form = new Form();
            form.param("email", "hoge@example.com");
            form.param("password", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/signin").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_password_null() throws Exception {
            Form form = new Form();
            form.param("email", "hoge@example.com");
            //form.param("password", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/signin").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_password_empty() throws Exception {
            Form form = new Form();
            form.param("email", "hoge@example.com");
            form.param("password", "");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/signin").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_email_null() throws Exception {
            Form form = new Form();
            //form.param("email", "hoge@example.com");
            form.param("password", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/signin").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_email_empty() throws Exception {
            Form form = new Form();
            //form.param("email", "hoge@example.com");
            form.param("password", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/signin").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_email_max_length() throws Exception {
            Form form = new Form();
            //@example.comは12文字
            form.param("email",
                    IntStream.range(12, 100).mapToObj(a -> "x").collect(Collectors.joining())
                            + "@example.com");
            form.param("password", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/signin").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_email_over_max_length() throws Exception {
            Form form = new Form();
            //@example.comは12文字
            form.param("email",
                    IntStream.range(12, 101).mapToObj(a -> "x").collect(Collectors.joining())
                            + "@example.com");
            form.param("password", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/signin").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(SigninResource.class).register(new Always200OK())
                    .register(new Dependencies());
        }
    }

    private static class Dependencies extends AbstractBinder {
        @Override
        protected void configure() {
            bind(new SecurityService() {
                @Override
                public boolean signin(EmailAddress email, String password) {
                    return true;
                }
            }).to(SecurityService.class);
            bind(new UserProvider()).to(UserProvider.class);
        }
    }
}
