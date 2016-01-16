package sealion.ui;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import sealion.domain.Key;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.model.TasksModel;
import sealion.service.TaskService;

@RequestScoped
@Path("projects/{project:\\d+}/tasks")
public class TaskResource {

    @PathParam("project")
    private Key<Project> project;

    @Inject
    private TaskService service;

    @GET
    public UIResponse list() {
        TasksModel model = new TasksModel();
        model.tasks = service.findByProject(project);
        return UIResponse.render("tasks", model);
    }

    @Path("new")
    @GET
    public UIResponse blank() {
        return UIResponse.render("new-task");
    }

    @Path("{id:\\d+}")
    @GET
    public UIResponse get(@PathParam("id") Key<Task> id) {
        return UIResponse.render("task");
    }
}
