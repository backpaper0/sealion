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

import sealion.domain.AccountRole;
import sealion.domain.Key;
import sealion.domain.ProjectName;
import sealion.entity.Project;
import sealion.model.ProjectsModel;
import sealion.service.ProjectService;
import sealion.ui.security.Permissions;
import sealion.ui.security.permission.AllowAll;

@RequestScoped
@Path("projects")
public class ProjectResource {

    @Inject
    private ProjectsModel.Builder projectsModelBuilder;
    @Inject
    private ProjectService projectService;

    @GET
    @Permissions(AllowAll.class)
    public UIResponse list() {
        ProjectsModel model = projectsModelBuilder.build();
        return UIResponse.render("projects", model);
    }

    @Path("new")
    @GET
    @Permissions(roles = { AccountRole.MANAGER, AccountRole.ADMIN })
    public UIResponse blank() {
        return UIResponse.render("new-project");
    }

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Permissions(roles = { AccountRole.MANAGER, AccountRole.ADMIN })
    public Response create(@NotNull @FormParam("name") ProjectName name, @Context UriInfo uriInfo) {
        Project project = projectService.create(name);
        URI location = uriInfo.getBaseUriBuilder().path(ProjectResource.class)
                .path(ProjectResource.class, "get").build(project.id);
        return Response.seeOther(location).build();
    }

    @Path("{id:\\d+}")
    @GET
    @Permissions(AllowAll.class)
    public Response get(@PathParam("id") Key<Project> id, @Context UriInfo uriInfo) {
        URI location = uriInfo.getBaseUriBuilder().path(TaskResource.class).build(id.getValue());
        return Response.temporaryRedirect(location).build();
    }
}
