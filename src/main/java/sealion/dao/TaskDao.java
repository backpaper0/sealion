package sealion.dao;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import sealion.domain.Key;
import sealion.entity.Project;
import sealion.entity.Task;
import sealion.model.TaskView;

@Dao
@AnnotateWith(annotations = {
        @Annotation(target = AnnotationTarget.CLASS, type = ApplicationScoped.class),
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Inject.class) })
public interface TaskDao {

    @Select
    List<TaskView> selectByProject(Key<Project> project);

    @Select
    Optional<TaskView> selectById(Key<Task> id);
}
