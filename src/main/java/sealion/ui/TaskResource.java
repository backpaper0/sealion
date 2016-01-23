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

import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.domain.TaskTitle;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.model.NewTaskModel;
import sealion.model.TaskModel;
import sealion.model.TasksModel;
import sealion.service.TaskService;

@RequestScoped
@Path("projects/{project:\\d+}/tasks")
public class TaskResource {

    @PathParam("project")
    private Key<Project> project;

    @Inject
    private TasksModel.Builder tasksModelBuilder;
    @Inject
    private NewTaskModel.Builder newTaskModelBuilder;
    @Inject
    private TaskModel.Builder taskModelBuilder;
    @Inject
    private TaskService taskService;

    @GET
    public UIResponse list() {
        TasksModel model = tasksModelBuilder.build(project);
        return UIResponse.render("tasks", model);
    }

    @Path("new")
    @GET
    public UIResponse blank() {
        NewTaskModel model = newTaskModelBuilder.build(project);
        return UIResponse.render("new-task", model);
    }

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@NotNull @FormParam("title") TaskTitle title,
            @FormParam("content") MarkedText content, @Context UriInfo uriInfo) {
        Task task = taskService.create(project, title, content);
        URI location = uriInfo.getBaseUriBuilder().path(TaskResource.class)
                .path(TaskResource.class, "get").build(project, task.id);
        return Response.seeOther(location).build();
    }

    @Path("{id:\\d+}")
    @GET
    public UIResponse get(@PathParam("id") Key<Task> id) {
        TaskModel model = taskModelBuilder.build(project, id);
        return UIResponse.render("task", model);
    }
}
