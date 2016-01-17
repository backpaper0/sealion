package sealion.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import sealion.domain.Key;
import sealion.entity.Project;

@Dao
@CdiManaged
public interface ProjectDao {

    @Select
    List<Project> selectAll();

    @Select
    Optional<Project> selectById(Key<Project> id);
}
