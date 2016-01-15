package sealion.ui;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import sealion.domain.Key;
import sealion.entity.Milestone;
import sealion.entity.Project;

@RequestScoped
@Path("projects/{project:\\d+}/milestones")
public class MilestoneResource {

    @PathParam("project")
    private Key<Project> project;

    @GET
    public UIResponse list() {
        return UIResponse.render("milestones");
    }

    @Path("new")
    @GET
    public UIResponse blank() {
        return UIResponse.render("new-milestone");
    }

    @Path("{id:\\d+}")
    @GET
    public UIResponse get(@PathParam("id") Key<Milestone> id) {
        return UIResponse.render("milestone");
    }
}
