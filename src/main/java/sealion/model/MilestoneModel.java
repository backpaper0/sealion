package sealion.model;

import java.util.Optional;

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

        public Optional<MilestoneModel> build(Key<Project> project, Key<Milestone> id) {
            return projectDao.selectById(project).flatMap(p -> {
                return milestoneDao.selectById(id).map(m -> {
                    MilestoneModel model = new MilestoneModel();
                    model.project = p;
                    model.milestone = m;
                    return model;
                });
            });
        }
    }
}
