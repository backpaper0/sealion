package sealion.ui;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sealion.domain.Key;
import sealion.entity.Project;
import sealion.model.ProjectsModel;

@RequestScoped
@Path("projects")
public class ProjectResource {

    @Inject
    private ProjectsModel.Builder projectsModelBuilder;

    @GET
    public UIResponse list() {
        ProjectsModel model = projectsModelBuilder.build();
        return UIResponse.render("projects", model);
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
