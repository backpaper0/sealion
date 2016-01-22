package sealion.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.ProjectDao;
import sealion.domain.ProjectName;
import sealion.entity.Project;

@ApplicationScoped
@Transactional
public class ProjectService {

    @Inject
    private ProjectDao projectDao;

    public Project create(ProjectName name) {
        Project entity = new Project();
        entity.name = name;
        projectDao.insert(entity);
        return entity;
    }
}
