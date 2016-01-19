package sealion.model;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.entity.Account;

public class AccountsModel {
    public List<Account> accounts;

    @Dependent
    public static class Builder {
        @Inject
        private AccountDao accountDao;

        public AccountsModel build() {
            AccountsModel model = new AccountsModel();
            model.accounts = accountDao.selectAll();
            return model;
        }
    }
}