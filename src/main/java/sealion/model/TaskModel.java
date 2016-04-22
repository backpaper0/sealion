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
    public final Project project;
    public final TaskView task;
    public final List<CommentView> comments;
    public final List<TaskStatus> status;
    public final List<Milestone> milestones;
    public final List<Account> accounts;

    private TaskModel(Project project, TaskView task, List<CommentView> comments,
            List<TaskStatus> status, List<Milestone> milestones, List<Account> accounts) {
        this.project = project;
        this.task = task;
        this.comments = comments;
        this.status = status;
        this.milestones = milestones;
        this.accounts = accounts;
    }

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
            List<CommentView> comments = commentDao.selectByTask(id);
            List<TaskStatus> status = Arrays.asList(TaskStatus.values());
            List<Milestone> milestones = milestoneDao.selectByProject(project);
            List<Account> accounts = accountDao.selectAll();
            return projectDao.selectById(project).flatMap(p -> taskDao.selectViewById(id)
                    .map(t -> new TaskModel(p, t, comments, status, milestones, accounts)));
        }
    }
}
