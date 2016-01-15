package sealion.ui;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sealion.domain.Key;
import sealion.entity.Project;

@RequestScoped
@Path("projects")
public class ProjectResource {

    @GET
    public UIResponse list() {
        return UIResponse.render("projects");
    }

    @Path("new")
    @GET
    public UIResponse blank() {
        return UIResponse.render("new-project");
    }

    @Path("{id:\\d+}")
    @GET
    public Response get(@PathParam("id") Key<Project> id, @Context UriInfo uriInfo) {
        URI location = uriInfo.getBaseUriBuilder().path(TaskResource.class).build(id.getValue());
        return Response.temporaryRedirect(location).build();
    }
}
