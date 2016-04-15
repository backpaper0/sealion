package sealion.model;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.domain.Key;
import sealion.entity.Project;

public class NewMilestoneModel {
    public Project project;

    @RequestScoped
    public static class Builder {
        @Inject
        private ProjectDao projectDao;

        public Optional<NewMilestoneModel> build(Key<Project> project) {
            return projectDao.selectById(project).map(p -> {
                NewMilestoneModel model = new NewMilestoneModel();
                model.project = p;
                return model;
            });
        }
    }
}
