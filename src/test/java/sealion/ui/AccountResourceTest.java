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

import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.domain.Username;
import sealion.entity.Account;
import sealion.model.AccountModel;
import sealion.model.AccountsModel;
import sealion.service.AccountService;

public class AccountResourceTest extends JerseyTest {

    @Test
    public void redirect_after_created() throws Exception {
        Form form = new Form();
        form.param("username", "x");
        form.param("email", "x");
        Entity<Form> entity = Entity.form(form);
        Response response = target("/accounts/new").request().post(entity);
        assertThat(response.getStatus()).isEqualTo(Status.SEE_OTHER.getStatusCode());
        assertThat(response.getLocation()).isEqualTo(getBaseUri().resolve("/accounts/1"));
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
                bind(new AccountsModel.Builder()).to(AccountsModel.Builder.class);
                bind(new AccountModel.Builder()).to(AccountModel.Builder.class);
                bind(new AccountService() {
                    @Override
                    public Account create(Username username, EmailAddress email) {
                        Account account = new Account();
                        account.id = new Key<>(1L);
                        return account;
                    }
                }).to(AccountService.class);
            }
        };
        return new ResourceConfig().register(AccountResource.class).register(binder);
    }
}
