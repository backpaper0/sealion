package sealion.ui;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sealion.service.SecurityService;
import sealion.session.UserProvider;

@RequestScoped
@Path("signout")
public class SignoutResource {

    @Inject
    private SecurityService securityService;
    @Inject
    private UserProvider userProvider;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response signout(@FormParam("csrfToken") String csrfToken, @Context UriInfo uriInfo) {
        if (userProvider.validateCsrfToken(csrfToken) == false) {
            throw new BadRequestException();
        }
        securityService.signout();
        URI location = uriInfo.getBaseUriBuilder().path(ProjectResource.class).build();
        return Response.seeOther(location).build();
    }
}
