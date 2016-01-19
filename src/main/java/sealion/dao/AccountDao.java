package sealion.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import sealion.domain.Key;
import sealion.entity.Account;

@Dao
@CdiManaged
public interface AccountDao {

    @Select
    List<Account> selectAll();

    @Select
    Optional<Account> selectById(Key<Account> id);
}