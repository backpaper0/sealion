package sealion.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.AccountDao;
import sealion.dao.GrantDao;
import sealion.domain.AccountRole;
import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.domain.Username;
import sealion.entity.Account;
import sealion.entity.Grant;

@ApplicationScoped
@Transactional
public class AccountService {

    @Inject
    private AccountDao accountDao;
    @Inject
    private GrantDao grantDao;

    public Account create(Username username, EmailAddress email) {
        Account entity = new Account();
        entity.username = username;
        entity.email = email;
        accountDao.insert(entity);
        return entity;
    }

    public void update(Key<Account> id, EmailAddress email, List<AccountRole> roles) {
        Account entity = accountDao.selectById(id).get();
        entity.email = email;
        accountDao.update(entity);

        grantDao.delete(grantDao.selectByAccount(id));
        grantDao.insert(roles.stream().map(a -> {
            Grant grant = new Grant();
            grant.account = id;
            grant.role = a;
            return grant;
        }).collect(Collectors.toList()));
    }
}
