package sealion.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import sealion.entity.Account;

@Dao
@AnnotateWith(annotations = {
        @Annotation(target = AnnotationTarget.CLASS, type = ApplicationScoped.class),
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Inject.class) })
public interface AccountDao {

    @Select
    List<Account> selectAll();
}
