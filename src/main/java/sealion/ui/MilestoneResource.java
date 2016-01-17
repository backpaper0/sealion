package sealion.ui;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import sealion.domain.Key;
import sealion.entity.Milestone;
import sealion.entity.Project;
import sealion.model.MilestoneModel;
import sealion.model.MilestonesModel;

@RequestScoped
@Path("projects/{project:\\d+}/milestones")
public class MilestoneResource {

    @PathParam("project")
    private Key<Project> project;
    @Inject
    private MilestonesModel.Builder milestonesModelBuilder;
    @Inject
    private MilestoneModel.Builder milestoneModelBuilder;

    @GET
    public UIResponse list() {
        MilestonesModel model = milestonesModelBuilder.build(project);
        return UIResponse.render("milestones", model);
    }

    @Path("new")
    @GET
    public UIResponse blank() {
        return UIResponse.render("new-milestone");
    }

    @Path("{id:\\d+}")
    @GET
    public UIResponse get(@PathParam("id") Key<Milestone> id) {
        MilestoneModel model = milestoneModelBuilder.build(project, id);
        return UIResponse.render("milestone", model);
    }
}
