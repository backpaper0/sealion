package sealion.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.BatchDelete;
import org.seasar.doma.Dao;
import org.seasar.doma.Entity;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import sealion.domain.Key;
import sealion.domain.MilestoneName;
import sealion.domain.Username;
import sealion.entity.Account;
import sealion.entity.Assignment;
import sealion.entity.Bundle;
import sealion.entity.Milestone;
import sealion.entity.Project;
import sealion.entity.Task;

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
    int[] deleteBundle(List<Bundle> entities);

    @Insert
    int insert(Bundle entity);

    @Select
    List<Assignment> selectAssignmentByTask(Key<Task> task);

    @BatchDelete
    int[] deleteAssignment(List<Assignment> entities);

    @Insert
    int insert(Assignment entity);

    @Entity
    public class TaskView extends Task {

        public Key<Milestone> milestone;
        public MilestoneName milestoneName;
        public Username accountName;
        public Key<Account> assignee;
        public Username assigneeName;
    }
}
