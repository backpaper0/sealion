package sealion.ui;

import java.net.URI;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import sealion.domain.EmailAddress;
import sealion.service.SecurityService;
import sealion.session.UserProvider;

@RequestScoped
@Path("signin")
public class SigninResource {

    @Inject
    private SecurityService securityService;
    @Inject
    private UserProvider userProvider;

    @GET
    public UIResponse get() {
        return UIResponse.render("signin");
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response signin(@NotNull @FormParam("email") EmailAddress email,
            @NotNull @Size(min = 1) @FormParam("password") String password,
            @FormParam("csrfToken") String csrfToken, @Context UriInfo uriInfo) {
        if (userProvider.validateCsrfToken(csrfToken) == false) {
            throw new BadRequestException();
        }
        if (securityService.signin(email, password) == false) {
            String errorMessage = "ログインできませんでした。ユーザーID、またはパスワードをお間違えではありませんか？";
            Response response = Response.status(Status.BAD_REQUEST)
                    .entity(UIResponse.render("signin", errorMessage)).build();
            throw new BadRequestException(response);
        }
        URI location = uriInfo.getBaseUriBuilder().path(ProjectResource.class).build();
        return Response.seeOther(location).build();
    }
}
