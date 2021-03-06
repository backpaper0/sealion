package sealion.ui;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.BadRequestException;
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

import sealion.domain.AccountRole;
import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.domain.Username;
import sealion.entity.Account;
import sealion.exception.DuplicateEmailException;
import sealion.model.AccountModel;
import sealion.model.AccountsModel;
import sealion.model.EditAccountModel;
import sealion.service.AccountService;
import sealion.service.SecurityService;
import sealion.ui.security.Permissions;
import sealion.ui.security.permission.AllowAll;
import sealion.ui.security.permission.SelfAccount;

@RequestScoped
@Path("accounts")
public class AccountResource {
    @Inject
    private AccountsModel.Builder accountsModelBuilder;
    @Inject
    private AccountModel.Builder accountModelBuilder;
    @Inject
    private EditAccountModel.Builder editAccountModelBuilder;
    @Inject
    private AccountService accountService;
    @Inject
    private SecurityService securityService;

    @GET
    @Permissions(roles = AccountRole.ADMIN)
    public UIResponse list() {
        AccountsModel model = accountsModelBuilder.build();
        return UIResponse.render("accounts", model);
    }

    @Path("new")
    @GET
    @Permissions(roles = AccountRole.ADMIN)
    public UIResponse blank() {
        return UIResponse.render("new-account");
    }

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Permissions(roles = AccountRole.ADMIN)
    public Response create(@NotNull @FormParam("username") Username username,
            @NotNull @FormParam("email") EmailAddress email,
            @NotNull @Size(min = 1) @FormParam("password") String password,
            @Context UriInfo uriInfo) throws DuplicateEmailException {
        Account account = accountService.create(username, email, password);
        URI location = uriInfo.getBaseUriBuilder().path(AccountResource.class).build(account.id);
        return Response.seeOther(location).build();
    }

    @Path("{id:\\d+}")
    @GET
    @Permissions(AllowAll.class)
    public Optional<UIResponse> get(@PathParam("id") Key<Account> id) {
        return accountModelBuilder.build(id).map(UIResponse.factory("account"));
    }

    @Path("{id:\\d+}/edit")
    @GET
    @Permissions(roles = AccountRole.ADMIN, value = SelfAccount.class)
    public Optional<UIResponse> edit(@PathParam("id") Key<Account> id) {
        return editAccountModelBuilder.build(id).map(UIResponse.factory("edit-account"));
    }

    @Path("{id:\\d+}/edit")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Permissions(roles = AccountRole.ADMIN, value = SelfAccount.class)
    public Response update(@PathParam("id") Key<Account> id,
            @NotNull @FormParam("email") EmailAddress email,
            @NotNull @FormParam("roles") List<AccountRole> roles, @Context UriInfo uriInfo)
            throws DuplicateEmailException {
        accountService.update(id, email, roles);
        URI location = uriInfo.getBaseUriBuilder().path(AccountResource.class).path("{id}")
                .build(id);
        return Response.seeOther(location).build();
    }

    @Path("{id:\\d+}/passwords/update")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Permissions(roles = AccountRole.ADMIN, value = SelfAccount.class)
    public Response updatePassword(@PathParam("id") Key<Account> id,
            @NotNull @Size(min = 1) @FormParam("oldPassword") String oldPassword,
            @NotNull @Size(min = 1) @FormParam("newPassword") String newPassword,
            @NotNull @Size(min = 1) @FormParam("confirmNewPassword") String confirmNewPassword,
            @Context UriInfo uriInfo) {
        if (newPassword.equals(confirmNewPassword) == false) {
            throw new BadRequestException();
        }
        if (securityService.update(id, oldPassword, newPassword) == false) {
            throw new BadRequestException();
        }
        URI location = uriInfo.getBaseUriBuilder().path(AccountResource.class).path("{id}")
                .build(id);
        return Response.seeOther(location).build();
    }
}
