package sealion.ui;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.service.CommentService;
import sealion.session.UserProvider;

@RequestScoped
@Path("projects/{project:\\d+}/tasks/{task:\\d+}/comments")
public class CommentResource {

    @PathParam("project")
    private Key<Project> project;
    @PathParam("task")
    private Key<Task> task;

    @Inject
    private CommentService commentService;
    @Inject
    private UserProvider userProvider;

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@NotNull @FormParam("content") MarkedText content,
            @FormParam("csrfToken") String csrfToken, @Context UriInfo uriInfo) {
        if (userProvider.validateCsrfToken(csrfToken) == false) {
            throw new BadRequestException();
        }
        commentService.create(task, content);
        URI location = uriInfo.getBaseUriBuilder().path(TaskResource.class)
                .path(TaskResource.class, "get").build(project, task);
        return Response.seeOther(location).build();
    }
}
