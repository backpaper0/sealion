package sealion.ui;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import sealion.domain.Key;
import sealion.entity.Project;
import sealion.entity.Task;

@RequestScoped
@Path("projects/{project:\\d+}/tasks")
public class TaskResource {

    @PathParam("project")
    private Key<Project> project;

    @GET
    public UIResponse list() {
        return UIResponse.render("tasks");
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
