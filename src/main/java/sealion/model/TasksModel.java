package sealion.model;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.dao.TaskDao;
import sealion.domain.Key;
import sealion.entity.Project;

public class TasksModel {
    public final Project project;
    public final List<TaskView> tasks;

    private TasksModel(Project project, List<TaskView> tasks) {
        this.project = project;
        this.tasks = tasks;
    }

    @RequestScoped
    public static class Builder {
        @Inject
        private TaskDao taskDao;
        @Inject
        private ProjectDao projectDao;

        public Optional<TasksModel> build(Key<Project> project) {
            List<TaskView> tasks = taskDao.selectByProject(project);
            return projectDao.selectById(project).map(p -> new TasksModel(p, tasks));
        }
    }
}
