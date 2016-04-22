package sealion.model;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.MilestoneDao;
import sealion.dao.ProjectDao;
import sealion.domain.Key;
import sealion.entity.Milestone;
import sealion.entity.Project;

public class MilestonesModel {
    public final Project project;
    public final List<Milestone> milestones;

    private MilestonesModel(Project project, List<Milestone> milestones) {
        this.project = project;
        this.milestones = milestones;
    }

    @RequestScoped
    public static class Builder {
        @Inject
        private MilestoneDao milestoneDao;
        @Inject
        private ProjectDao projectDao;

        public Optional<MilestonesModel> build(Key<Project> project) {
            List<Milestone> milestones = milestoneDao.selectByProject(project);
            return projectDao.selectById(project).map(p -> new MilestonesModel(p, milestones));
        }
    }
}
