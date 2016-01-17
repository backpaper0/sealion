package sealion.model;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import sealion.dao.MilestoneDao;
import sealion.dao.ProjectDao;
import sealion.domain.Key;
import sealion.entity.Milestone;
import sealion.entity.Project;

public class MilestonesModel {
    public Project project;
    public List<Milestone> milestones;

    @Dependent
    public static class Builder {
        @Inject
        private MilestoneDao milestoneDao;
        @Inject
        private ProjectDao projectDao;

        public MilestonesModel build(Key<Project> project) {
            MilestonesModel model = new MilestonesModel();
            model.project = projectDao.selectById(project).get();
            model.milestones = milestoneDao.selectByProject(project);
            return model;
        }
    }
}
