package sealion.dao;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import sealion.domain.Key;
import sealion.entity.Account;
import sealion.entity.Password;

@Dao
@CdiManaged
public interface PasswordDao {

    @Select
    Optional<Password> selectByAccount(Key<Account> account);

    @Update
    int update(Password entity);
}
