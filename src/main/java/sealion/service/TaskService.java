package sealion.service;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.inject.Inject;

import sealion.dao.TaskDao;
import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.domain.PostedDate;
import sealion.domain.TaskStatus;
import sealion.domain.TaskTitle;
import sealion.entity.Account;
import sealion.entity.Assignment;
import sealion.entity.Bundle;
import sealion.entity.Milestone;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.session.User;

@Service
public class TaskService {

    @Inject
    private TaskDao taskDao;
    @Inject
    private User user;
    @Inject
    private Clock clock;

    public Task create(Key<Project> project, TaskTitle title, MarkedText content) {
        Task entity = new Task();
        entity.title = title;
        entity.content = content;
        entity.status = TaskStatus.OPEN;
        entity.postedBy = user.getAccount().id;
        entity.postedAt = new PostedDate(LocalDateTime.now(clock));
        entity.project = project;
        taskDao.insert(entity);
        return entity;
    }

    public void changeStatus(Key<Task> id, TaskStatus status) {
        Task entity = taskDao.selectById(id).get();
        entity.status = status;
        taskDao.update(entity);
    }

    public void setMilestone(Key<Task> id, Key<Milestone> milestone) {
        taskDao.deleteBundle(taskDao.selectBundleByTask(id));
        Bundle entity = new Bundle();
        entity.task = id;
        entity.milestone = milestone;
        taskDao.insert(entity);
    }

    public void setAssignee(Key<Task> id, Key<Account> account) {
        taskDao.deleteAssignment(taskDao.selectAssignmentByTask(id));
        Assignment entity = new Assignment();
        entity.task = id;
        entity.account = account;
        taskDao.insert(entity);
    }
}
