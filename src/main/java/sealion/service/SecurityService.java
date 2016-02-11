package sealion.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.AccountDao;
import sealion.dao.PasswordDao;
import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.domain.PasswordHash;
import sealion.entity.Account;
import sealion.entity.Password;
import sealion.session.SessionKey;

@ApplicationScoped
@Transactional
public class SecurityService {

    @Inject
    private AccountDao accountDao;
    @Inject
    private PasswordDao passwordDao;
    @Inject
    private SessionKey sessionKey;

    public boolean update(Key<Account> id, String oldPassword, String newPassword) {
        Password entity = passwordDao.selectByAccount(id).get();
        if (entity.hash.getValue().equals(oldPassword) == false) {
            return false;
        }
        entity.hash = new PasswordHash(newPassword);
        passwordDao.update(entity);
        return true;
    }

    public boolean signin(EmailAddress email, String password) {
        Optional<Account> accountOption = accountDao.selectByEmail(email);
        if (accountOption.isPresent() == false) {
            return false;
        }
        Account account = accountOption.get();
        Password entity = passwordDao.selectByAccount(account.id).get();
        if (entity.hash.getValue().equals(password) == false) {
            return false;
        }
        sessionKey.set(account.id);
        return true;
    }
}
