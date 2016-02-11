package sealion.ui;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
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

import sealion.domain.AccountRole;
import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.domain.Username;
import sealion.entity.Account;
import sealion.model.AccountsModel;
import sealion.model.EditAccountModel;
import sealion.service.AccountService;
import sealion.service.SecurityService;
import sealion.test.Always200OK;

@RunWith(Enclosed.class)
public class AccountResourceTest {

    public static class RoutingTest extends JerseyTest {

        @Test
        public void redirect_after_created() throws Exception {
            Form form = new Form();
            form.param("username", "x");
            form.param("email", "x");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.SEE_OTHER.getStatusCode());
            assertThat(response.getLocation()).isEqualTo(getBaseUri().resolve("/accounts"));
        }

        @Test
        public void redirect_after_update() throws Exception {
            Form form = new Form();
            form.param("username", "x");
            form.param("email", "x");
            Arrays.stream(AccountRole.values()).forEach(a -> form.param("roles", a.toString()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/1/edit").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.SEE_OTHER.getStatusCode());
            assertThat(response.getLocation()).isEqualTo(getBaseUri().resolve("/accounts"));
        }

        //TODO パスワード変更後のルーティングのテスト

        @Override
        protected void configureClient(ClientConfig config) {
            config.property(ClientProperties.FOLLOW_REDIRECTS, false);
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(AccountResource.class)
                    .register(new Dependencies());
        }
    }

    public static class ValidationTest extends JerseyTest {

        @Test
        public void validate() throws Exception {
            Form form = new Form();
            form.param("username", "x");
            form.param("email", "hoge@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_username_null() throws Exception {
            Form form = new Form();
            //form.param("username", "x");
            form.param("email", "hoge@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_username_empty() throws Exception {
            Form form = new Form();
            form.param("username", "");
            form.param("email", "hoge@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_username_max_length() throws Exception {
            Form form = new Form();
            form.param("username",
                    IntStream.range(0, 20).mapToObj(a -> "x").collect(Collectors.joining()));
            form.param("email", "hoge@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_username_over_max_length() throws Exception {
            Form form = new Form();
            form.param("username",
                    IntStream.range(0, 21).mapToObj(a -> "x").collect(Collectors.joining()));
            form.param("email", "hoge@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_email_null() throws Exception {
            Form form = new Form();
            form.param("username", "x");
            //form.param("email", "hoge@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_email_empty() throws Exception {
            Form form = new Form();
            form.param("username", "x");
            //form.param("email", "hoge@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_email_max_length() throws Exception {
            Form form = new Form();
            form.param("username", "x");
            //@example.comは12文字
            form.param("email",
                    IntStream.range(12, 100).mapToObj(a -> "x").collect(Collectors.joining())
                            + "@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_email_over_max_length() throws Exception {
            Form form = new Form();
            form.param("username", "x");
            //@example.comは12文字
            form.param("email",
                    IntStream.range(12, 101).mapToObj(a -> "x").collect(Collectors.joining())
                            + "@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/new").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(AccountResource.class).register(new Always200OK())
                    .register(new Dependencies());
        }
    }

    public static class ValidationEditTest extends JerseyTest {

        @Test
        public void validate() throws Exception {
            Form form = new Form();
            form.param("email", "hoge@example.com");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/1/edit").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_email_null() throws Exception {
            Form form = new Form();
            //form.param("email", "hoge@example.com");
            Arrays.stream(AccountRole.values()).forEach(a -> form.param("roles", a.toString()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/1/edit").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_email_empty() throws Exception {
            Form form = new Form();
            //form.param("email", "hoge@example.com");
            Arrays.stream(AccountRole.values()).forEach(a -> form.param("roles", a.toString()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/1/edit").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_email_max_length() throws Exception {
            Form form = new Form();
            //@example.comは12文字
            form.param("email",
                    IntStream.range(12, 100).mapToObj(a -> "x").collect(Collectors.joining())
                            + "@example.com");
            Arrays.stream(AccountRole.values()).forEach(a -> form.param("roles", a.toString()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/1/edit").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
        }

        @Test
        public void validate_email_over_max_length() throws Exception {
            Form form = new Form();
            //@example.comは12文字
            form.param("email",
                    IntStream.range(12, 101).mapToObj(a -> "x").collect(Collectors.joining())
                            + "@example.com");
            Arrays.stream(AccountRole.values()).forEach(a -> form.param("roles", a.toString()));
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/1/edit").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        public void validate_roles_unknown() throws Exception {
            Form form = new Form();
            form.param("email", "hoge@example.com");
            form.param("roles", "UNKNOWN");
            Entity<Form> entity = Entity.form(form);
            Response response = target("/accounts/1/edit").request().post(entity);
            assertThat(response.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        }

        @Override
        protected Application configure() {
            return new ResourceConfig().register(AccountResource.class).register(new Always200OK())
                    .register(new Dependencies());
        }
    }

    //TODO パスワード変更のバリデーションのテスト

    private static class Dependencies extends AbstractBinder {
        @Override
        protected void configure() {
            bind(new AccountsModel.Builder()).to(AccountsModel.Builder.class);
            bind(new EditAccountModel.Builder()).to(EditAccountModel.Builder.class);
            bind(new AccountService() {
                @Override
                public Account create(Username username, EmailAddress email) {
                    Account account = new Account();
                    account.id = new Key<>(1L);
                    return account;
                }

                @Override
                public void update(Key<Account> id, EmailAddress email, List<AccountRole> roles) {
                }
            }).to(AccountService.class);
            bind(new SecurityService()).to(SecurityService.class);
        }
    }
}
