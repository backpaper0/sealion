package sealion.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.ProjectDao;
import sealion.entity.Project;

@ApplicationScoped
@Transactional
public class ProjectService {

    @Inject
    private ProjectDao dao;

    public List<Project> findAll() {
        return dao.selectAll();
    }
}
