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
    public final Project project;
    public final Milestone milestone;

    private MilestoneModel(Project project, Milestone milestone) {
        this.project = project;
        this.milestone = milestone;
    }

    @RequestScoped
    public static class Builder {
        @Inject
        private MilestoneDao milestoneDao;
        @Inject
        private ProjectDao projectDao;

        public Optional<MilestoneModel> build(Key<Project> project, Key<Milestone> id) {
            return projectDao.selectById(project)
                    .flatMap(p -> milestoneDao.selectById(id).map(m -> new MilestoneModel(p, m)));
        }
    }
}
