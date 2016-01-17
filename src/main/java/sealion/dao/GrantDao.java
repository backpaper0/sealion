package sealion.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import sealion.domain.Key;
import sealion.entity.Account;
import sealion.entity.Grant;

@Dao
@CdiManaged
public interface GrantDao {

    @Select
    List<Grant> selectByAccount(Key<Account> account);
}
