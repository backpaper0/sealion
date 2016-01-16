package sealion.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.TaskDao;
import sealion.domain.Key;
import sealion.entity.Project;
import sealion.entity.Task;

@ApplicationScoped
@Transactional
public class TaskService {

    @Inject
    private TaskDao dao;

    public List<Task> findByProject(Key<Project> project) {
        return dao.selectByProject(project);
    }
}
