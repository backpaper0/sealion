package sealion.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.domain.Key;
import sealion.entity.Project;

public class NewMilestoneModel {
    public Project project;

    @Dependent
    public static class Builder {
        @Inject
        private ProjectDao projectDao;

        public NewMilestoneModel build(Key<Project> project) {
            NewMilestoneModel model = new NewMilestoneModel();
            model.project = projectDao.selectById(project).get();
            return model;
        }
    }
}
