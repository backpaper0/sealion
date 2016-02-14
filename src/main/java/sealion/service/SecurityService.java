package sealion.service;

import java.util.Optional;

import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.PasswordDao;
import sealion.domain.EmailAddress;
import sealion.domain.HashAlgorithm;
import sealion.domain.Key;
import sealion.domain.PasswordHash;
import sealion.domain.Salt;
import sealion.entity.Account;
import sealion.entity.Password;
import sealion.session.SessionKey;

@Service
public class SecurityService {

    @Inject
    private AccountDao accountDao;
    @Inject
    private PasswordDao passwordDao;
    @Inject
    private SessionKey sessionKey;

    public void create(Key<Account> account, String password) {
        Password entity = new Password();
        entity.account = account;
        entity.hash = new PasswordHash(password);
        entity.salt = new Salt("none");
        entity.hashAlgorithm = HashAlgorithm.PLAIN;
        passwordDao.insert(entity);
    }

    public boolean update(Key<Account> account, String oldPassword, String newPassword) {
        Password entity = passwordDao.selectByAccount(account).get();
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

    public void signout() {
        sessionKey.clear();
    }
}
