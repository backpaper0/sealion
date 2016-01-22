package sealion.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;

import sealion.domain.Key;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.model.TaskView;

@Dao
@CdiManaged
public interface TaskDao {

    @Select
    List<TaskView> selectByProject(Key<Project> project);

    @Select
    Optional<TaskView> selectById(Key<Task> id);

    @Insert
    int insert(Task entity);
}
