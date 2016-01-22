package sealion.model;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.CommentDao;
import sealion.dao.ProjectDao;
import sealion.dao.TaskDao;
import sealion.domain.Key;
import sealion.entity.Project;
import sealion.entity.Task;

public class TaskModel {
    public Project project;
    public TaskView task;
    public List<CommentView> comments;

    @RequestScoped
    public static class Builder {
        @Inject
        private TaskDao taskDao;
        @Inject
        private ProjectDao projectDao;
        @Inject
        private CommentDao commentDao;

        public TaskModel build(Key<Project> project, Key<Task> id) {
            TaskModel model = new TaskModel();
            model.project = projectDao.selectById(project).get();
            model.task = taskDao.selectById(id).get();
            model.comments = commentDao.selectByTask(id);
            return model;
        }
    }
}
