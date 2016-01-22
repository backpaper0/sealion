package sealion.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.MilestoneDao;
import sealion.dao.ProjectDao;
import sealion.domain.Key;
import sealion.entity.Milestone;
import sealion.entity.Project;

public class MilestoneModel {
    public Project project;
    public Milestone milestone;

    @RequestScoped
    public static class Builder {
        @Inject
        private MilestoneDao milestoneDao;
        @Inject
        private ProjectDao projectDao;

        public MilestoneModel build(Key<Project> project, Key<Milestone> id) {
            MilestoneModel model = new MilestoneModel();
            model.project = projectDao.selectById(project).get();
            model.milestone = milestoneDao.selectById(id).get();
            return model;
        }
    }
}
