package sealion.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.GrantDao;
import sealion.domain.AccountRole;
import sealion.domain.Key;
import sealion.entity.Account;

public class AccountModel {
    public final Account account;
    public final List<AccountRole> roles;

    private AccountModel(Account account, List<AccountRole> roles) {
        this.account = account;
        this.roles = roles;
    }

    @RequestScoped
    public static class Builder {
        @Inject
        private AccountDao accountDao;
        @Inject
        private GrantDao grantDao;

        public Optional<AccountModel> build(Key<Account> id) {
            return accountDao.selectById(id).map(a -> new AccountModel(a, grantDao
                    .selectByAccount(id).stream().map(b -> b.role).collect(Collectors.toList())));
        }
    }
}
