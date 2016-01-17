package sealion.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import sealion.domain.Key;
import sealion.entity.Milestone;
import sealion.entity.Project;

@Dao
@CdiManaged
public interface MilestoneDao {

    @Select
    List<Milestone> selectByProject(Key<Project> project);

    @Select
    Optional<Milestone> selectById(Key<Milestone> id);
}
