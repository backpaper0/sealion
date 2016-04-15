package sealion.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.CommentDao;
import sealion.dao.MilestoneDao;
import sealion.dao.ProjectDao;
import sealion.dao.TaskDao;
import sealion.domain.Key;
import sealion.domain.TaskStatus;
import sealion.entity.Account;
import sealion.entity.Milestone;
import sealion.entity.Project;
import sealion.entity.Task;

public class TaskModel {
    public Project project;
    public TaskView task;
    public List<CommentView> comments;
    public List<TaskStatus> status;
    public List<Milestone> milestones;
    public List<Account> accounts;

    @RequestScoped
    public static class Builder {
        @Inject
        private TaskDao taskDao;
        @Inject
        private ProjectDao projectDao;
        @Inject
        private CommentDao commentDao;
        @Inject
        private MilestoneDao milestoneDao;
        @Inject
        private AccountDao accountDao;

        public Optional<TaskModel> build(Key<Project> project, Key<Task> id) {
            return projectDao.selectById(project).flatMap(p -> {
                return taskDao.selectViewById(id).map(t -> {
                    TaskModel model = new TaskModel();
                    model.project = p;
                    model.task = t;
                    model.comments = commentDao.selectByTask(id);
                    model.status = Arrays.asList(TaskStatus.values());
                    model.milestones = milestoneDao.selectByProject(project);
                    model.accounts = accountDao.selectAll();
                    return model;
                });
            });
        }
    }
}
