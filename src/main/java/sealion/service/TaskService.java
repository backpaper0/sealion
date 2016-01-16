package sealion.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.ProjectDao;
import sealion.dao.TaskDao;
import sealion.domain.Key;
import sealion.entity.Project;
import sealion.model.TasksModel;

@ApplicationScoped
@Transactional
public class TaskService {

    @Inject
    private TaskDao dao;
    @Inject
    private ProjectDao projectDao;

    public TasksModel findByProject(Key<Project> project) {
        TasksModel model = new TasksModel();
        model.project = projectDao.selectById(project).get();
        model.tasks = dao.selectByProject(project);
        return model;
    }
}
