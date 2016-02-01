package sealion.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.BatchDelete;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import sealion.domain.Key;
import sealion.entity.Bundle;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.model.TaskView;

@Dao
@CdiManaged
public interface TaskDao {

    @Select
    List<TaskView> selectByProject(Key<Project> project);

    @Select
    Optional<TaskView> selectViewById(Key<Task> id);

    @Select
    Optional<Task> selectById(Key<Task> id);

    @Insert
    int insert(Task entity);

    @Update
    int update(Task entity);

    @Select
    List<Bundle> selectBundleByTask(Key<Task> task);

    @BatchDelete
    int[] delete(List<Bundle> entities);

    @Insert
    int insert(Bundle entity);
}
