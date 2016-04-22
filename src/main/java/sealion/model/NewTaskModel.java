package sealion.model;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.domain.Key;
import sealion.entity.Project;

public class NewTaskModel {
    public final Project project;

    private NewTaskModel(Project project) {
        this.project = project;
    }

    @RequestScoped
    public static class Builder {
        @Inject
        private ProjectDao projectDao;

        public Optional<NewTaskModel> build(Key<Project> project) {
            return projectDao.selectById(project).map(p -> {
                return new NewTaskModel(p);
            });
        }
    }
}
