package sealion.model;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.domain.Key;
import sealion.entity.Project;

public class NewTaskModel {
    public Project project;

    @RequestScoped
    public static class Builder {
        @Inject
        private ProjectDao projectDao;

        public Optional<NewTaskModel> build(Key<Project> project) {
            return projectDao.selectById(project).map(p -> {
                NewTaskModel model = new NewTaskModel();
                model.project = p;
                return model;
            });
        }
    }
}
