package sealion.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.entity.Account;

@Dao
@CdiManaged
public interface AccountDao {

    @Select
    List<Account> selectAll();

    @Select
    Optional<Account> selectById(Key<Account> id);

    @Select
    Optional<Account> selectByEmail(EmailAddress email);

    @Insert
    int insert(Account entity);

    @Update
    int update(Account entity);
}
