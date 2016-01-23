package sealion.ui;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.domain.Username;
import sealion.entity.Account;
import sealion.model.AccountModel;
import sealion.model.AccountsModel;
import sealion.service.AccountService;

@RequestScoped
@Path("accounts")
public class AccountResource {
    @Inject
    private AccountsModel.Builder accountsModelBuilder;
    @Inject
    private AccountModel.Builder accountModelBuilder;
    @Inject
    private AccountService accountService;

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

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@NotNull @FormParam("username") Username username,
            @NotNull @FormParam("email") EmailAddress email, @Context UriInfo uriInfo) {
        Account account = accountService.create(username, email);
        URI location = uriInfo.getBaseUriBuilder().path(AccountResource.class)
                .path(AccountResource.class, "get").build(account.id);
        return Response.seeOther(location).build();
    }

    @Path("{id:\\d+}")
    @GET
    public UIResponse get(@PathParam("id") Key<Account> id) {
        AccountModel model = accountModelBuilder.build(id);
        return UIResponse.render("account", model);
    }
}
