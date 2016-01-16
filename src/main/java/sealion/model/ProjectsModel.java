package sealion.model;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.entity.Project;

public class ProjectsModel {
    public List<Project> projects;

    @Dependent
    public static class Builder {

        @Inject
        private ProjectDao projectDao;

        public ProjectsModel build() {
            ProjectsModel model = new ProjectsModel();
            model.projects = projectDao.selectAll();
            return model;
        }
    }
}
