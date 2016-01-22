package sealion.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.AccountDao;
import sealion.domain.EmailAddress;
import sealion.domain.Username;
import sealion.entity.Account;

@ApplicationScoped
@Transactional
public class AccountService {

    @Inject
    private AccountDao accountDao;

    public Account create(Username username, EmailAddress email) {
        Account entity = new Account();
        entity.username = username;
        entity.email = email;
        accountDao.insert(entity);
        return entity;
    }
}
