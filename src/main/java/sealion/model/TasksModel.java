package sealion.model;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.dao.TaskDao;
import sealion.domain.Key;
import sealion.entity.Project;

public class TasksModel {
    public Project project;
    public List<TaskView> tasks;

    @Dependent
    public static class Builder {
        @Inject
        private TaskDao taskDao;
        @Inject
        private ProjectDao projectDao;

        public TasksModel build(Key<Project> project) {
            TasksModel model = new TasksModel();
            model.project = projectDao.selectById(project).get();
            model.tasks = taskDao.selectByProject(project);
            return model;
        }
    }
}
