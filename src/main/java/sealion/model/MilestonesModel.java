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
    public Project project;
    public List<Milestone> milestones;

    @RequestScoped
    public static class Builder {
        @Inject
        private MilestoneDao milestoneDao;
        @Inject
        private ProjectDao projectDao;

        public Optional<MilestonesModel> build(Key<Project> project) {
            return projectDao.selectById(project).map(p -> {
                MilestonesModel model = new MilestonesModel();
                model.project = p;
                model.milestones = milestoneDao.selectByProject(project);
                return model;
            });
        }
    }
}
