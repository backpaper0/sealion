package sealion.ui;

import java.net.URI;
import java.util.Optional;

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
import sealion.domain.TaskStatus;
import sealion.domain.TaskTitle;
import sealion.entity.Account;
import sealion.entity.Milestone;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.model.NewTaskModel;
import sealion.model.TaskModel;
import sealion.model.TasksModel;
import sealion.service.TaskService;
import sealion.ui.security.Permissions;
import sealion.ui.security.permission.AllowAll;
import sealion.ui.security.permission.SignedIn;

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
    @Permissions(AllowAll.class)
    public Optional<UIResponse> list() {
        return tasksModelBuilder.build(project).map(UIResponse.factory("tasks"));
    }

    @Path("new")
    @GET
    @Permissions(SignedIn.class)
    public Optional<UIResponse> blank() {
        return newTaskModelBuilder.build(project).map(UIResponse.factory("new-task"));
    }

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Permissions(SignedIn.class)
    public Response create(@NotNull @FormParam("title") TaskTitle title,
            @FormParam("content") MarkedText content, @Context UriInfo uriInfo) {
        Task task = taskService.create(project, title, content);
        URI location = uriInfo.getBaseUriBuilder().path(TaskResource.class)
                .path(TaskResource.class, "get").build(project, task.id);
        return Response.seeOther(location).build();
    }

    @Path("{id:\\d+}")
    @GET
    @Permissions(AllowAll.class)
    public Optional<UIResponse> get(@PathParam("id") Key<Task> id) {
        return taskModelBuilder.build(project, id).map(UIResponse.factory("task"));
    }

    @Path("{id:\\d+}:status")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Permissions(SignedIn.class)
    public void changeStatus(@PathParam("id") Key<Task> id,
            @FormParam("status") TaskStatus status) {
        taskService.changeStatus(id, status);
    }

    @Path("{id:\\d+}:milestone")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Permissions(SignedIn.class)
    public void setMilestone(@PathParam("id") Key<Task> id,
            @FormParam("milestone") Key<Milestone> milestone) {
        taskService.setMilestone(id, milestone);
    }

    @Path("{id:\\d+}:assignee")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Permissions(SignedIn.class)
    public void setAssignee(@PathParam("id") Key<Task> id,
            @FormParam("account") Key<Account> account) {
        taskService.setAssignee(id, account);
    }
}
