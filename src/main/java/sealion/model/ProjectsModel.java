package sealion.model;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.entity.Project;

public class ProjectsModel {
    public final List<Project> projects;

    private ProjectsModel(List<Project> projects) {
        this.projects = projects;
    }

    @RequestScoped
    public static class Builder {

        @Inject
        private ProjectDao projectDao;

        public ProjectsModel build() {
            List<Project> projects = projectDao.selectAll();
            return new ProjectsModel(projects);
        }
    }
}
