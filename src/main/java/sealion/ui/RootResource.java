package sealion.ui;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sealion.ui.security.Permissions;
import sealion.ui.security.permission.AllowAll;

@RequestScoped
@Path("")
public class RootResource {

    @GET
    @Permissions(AllowAll.class)
    public Response redirect(@Context UriInfo uriInfo) {
        URI location = uriInfo.getBaseUriBuilder().path(ProjectResource.class).build();
        return Response.temporaryRedirect(location).build();
    }
}
