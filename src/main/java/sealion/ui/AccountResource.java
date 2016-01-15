package sealion.ui;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import sealion.domain.Key;
import sealion.entity.Account;

@RequestScoped
@Path("accounts")
public class AccountResource {

    @GET
    public UIResponse list() {
        return UIResponse.render("accounts");
    }

    @Path("new")
    @GET
    public UIResponse blank() {
        return UIResponse.render("new-account");
    }

    @Path("{id:\\d+}")
    @GET
    public UIResponse get(@PathParam("id") Key<Account> id) {
        return UIResponse.render("account");
    }
}
