package sealion.ui;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import sealion.domain.Key;
import sealion.entity.Account;
import sealion.model.AccountModel;
import sealion.model.AccountsModel;

@RequestScoped
@Path("accounts")
public class AccountResource {
    @Inject
    private AccountsModel.Builder accountsModelBuilder;
    @Inject
    private AccountModel.Builder accountModelBuilder;

    @GET
    public UIResponse list() {
        AccountsModel model = accountsModelBuilder.build();
        return UIResponse.render("accounts", model);
    }

    @Path("new")
    @GET
    public UIResponse blank() {
        return UIResponse.render("new-account");
    }

    @Path("{id:\\d+}")
    @GET
    public UIResponse get(@PathParam("id") Key<Account> id) {
        AccountModel model = accountModelBuilder.build(id);
        return UIResponse.render("account", model);
    }
}
