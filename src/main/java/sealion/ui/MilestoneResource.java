package sealion.ui;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
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

import sealion.domain.FixedDate;
import sealion.domain.Key;
import sealion.domain.MilestoneName;
import sealion.entity.Milestone;
import sealion.entity.Project;
import sealion.model.MilestoneModel;
import sealion.model.MilestonesModel;
import sealion.model.NewMilestoneModel;
import sealion.service.MilestoneService;
import sealion.session.UserProvider;

@RequestScoped
@Path("projects/{project:\\d+}/milestones")
public class MilestoneResource {

    @PathParam("project")
    private Key<Project> project;
    @Inject
    private MilestonesModel.Builder milestonesModelBuilder;
    @Inject
    private NewMilestoneModel.Builder newMilestoneModelBuilder;
    @Inject
    private MilestoneModel.Builder milestoneModelBuilder;
    @Inject
    private MilestoneService milestoneService;
    @Inject
    private UserProvider userProvider;

    @GET
    public UIResponse list() {
        MilestonesModel model = milestonesModelBuilder.build(project);
        return UIResponse.render("milestones", model);
    }

    @Path("new")
    @GET
    public UIResponse blank() {
        NewMilestoneModel model = newMilestoneModelBuilder.build(project);
        return UIResponse.render("new-milestone", model);
    }

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@NotNull @FormParam("name") MilestoneName name,
            @FormParam("fixedDate") FixedDate fixedDate, @FormParam("csrfToken") String csrfToken,
            @Context UriInfo uriInfo) {
        if (userProvider.validateCsrfToken(csrfToken) == false) {
            throw new BadRequestException();
        }
        Milestone milestone = milestoneService.create(project, name, fixedDate);
        URI location = uriInfo.getBaseUriBuilder().path(MilestoneResource.class)
                .path(MilestoneResource.class, "get").build(project, milestone.id);
        return Response.seeOther(location).build();
    }

    @Path("{id:\\d+}")
    @GET
    public UIResponse get(@PathParam("id") Key<Milestone> id) {
        MilestoneModel model = milestoneModelBuilder.build(project, id);
        return UIResponse.render("milestone", model);
    }
}
