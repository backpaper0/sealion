package sealion.ui;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sealion.service.SecurityService;

@RequestScoped
@Path("signout")
public class SignoutResource {

    @Inject
    private SecurityService securityService;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response signout(@Context UriInfo uriInfo) {
        securityService.signout();
        URI location = uriInfo.getBaseUriBuilder().path(ProjectResource.class).build();
        return Response.seeOther(location).build();
    }
}
