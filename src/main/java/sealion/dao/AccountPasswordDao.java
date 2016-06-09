package sealion.dao;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import sealion.domain.Key;
import sealion.entity.Account;
import sealion.entity.AccountPassword;

@Dao
@CdiManaged
public interface AccountPasswordDao {

    @Select
    Optional<AccountPassword> selectByAccount(Key<Account> account);

    @Update
    int update(AccountPassword entity);

    @Insert
    int insert(AccountPassword entity);
}
