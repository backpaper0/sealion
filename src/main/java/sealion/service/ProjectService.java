package sealion.service;

import javax.inject.Inject;

import sealion.dao.ProjectDao;
import sealion.domain.ProjectName;
import sealion.entity.Project;

@Service
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
