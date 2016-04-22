package sealion.model;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.entity.Account;

public class AccountsModel {
    public final List<Account> accounts;

    private AccountsModel(List<Account> accounts) {
        this.accounts = accounts;
    }

    @RequestScoped
    public static class Builder {
        @Inject
        private AccountDao accountDao;

        public AccountsModel build() {
            List<Account> accounts = accountDao.selectAll();
            return new AccountsModel(accounts);
        }
    }
}
