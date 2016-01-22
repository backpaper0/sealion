package sealion.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;

import sealion.domain.Key;
import sealion.entity.Comment;
import sealion.entity.Task;
import sealion.model.CommentView;

@Dao
@CdiManaged
public interface CommentDao {

    @Select
    List<CommentView> selectByTask(Key<Task> task);

    @Insert
    int insert(Comment entity);
}
