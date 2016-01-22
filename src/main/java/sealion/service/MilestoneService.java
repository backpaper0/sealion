package sealion.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.MilestoneDao;
import sealion.domain.FixedDate;
import sealion.domain.Key;
import sealion.domain.MilestoneName;
import sealion.entity.Milestone;
import sealion.entity.Project;

@ApplicationScoped
@Transactional
public class MilestoneService {

    @Inject
    private MilestoneDao milestoneDao;

    public Milestone create(Key<Project> project, MilestoneName name, FixedDate fixedDate) {
        Milestone entity = new Milestone();
        entity.name = name;
        entity.fixedDate = fixedDate;
        entity.project = project;
        milestoneDao.insert(entity);
        return entity;
    }
}
