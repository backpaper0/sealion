package sealion.model;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.GrantDao;
import sealion.domain.Key;
import sealion.entity.Account;
import sealion.entity.Grant;

public class AccountModel {
    public Account account;
    public List<Grant> grant;

    @Dependent
    public static class Builder {
        @Inject
        private AccountDao accountDao;
        @Inject
        private GrantDao grantDao;

        public AccountModel build(Key<Account> id) {
            AccountModel model = new AccountModel();
            model.account = accountDao.selectById(id).get();
            model.grant = grantDao.selectByAccount(id);
            return model;
        }
    }
}
