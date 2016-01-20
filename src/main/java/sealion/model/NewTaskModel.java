package sealion.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.domain.Key;
import sealion.entity.Project;

public class NewTaskModel {
    public Project project;

    @Dependent
    public static class Builder {
        @Inject
        private ProjectDao projectDao;

        public NewTaskModel build(Key<Project> project) {
            NewTaskModel model = new NewTaskModel();
            model.project = projectDao.selectById(project).get();
            return model;
        }
    }
}
