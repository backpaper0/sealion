package sealion.service;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.TaskDao;
import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.domain.PostedDate;
import sealion.domain.TaskStatus;
import sealion.domain.TaskTitle;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.session.User;

@ApplicationScoped
@Transactional
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
}
