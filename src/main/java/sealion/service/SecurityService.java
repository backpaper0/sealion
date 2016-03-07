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
import sealion.session.UserProvider;

@Service
public class SecurityService {

    @Inject
    private AccountDao accountDao;
    @Inject
    private PasswordDao passwordDao;
    @Inject
    private UserProvider userProvider;

    public void create(Key<Account> account, String password) {
        Salt salt = Salt.generate();
        Password entity = new Password();
        entity.account = account;
        entity.hash = PasswordHash.hash(password, salt);
        entity.salt = salt;
        entity.hashAlgorithm = HashAlgorithm.SHA512;
        passwordDao.insert(entity);
    }

    public boolean update(Key<Account> account, String oldPassword, String newPassword) {
        Password entity = passwordDao.selectByAccount(account).get();
        if (entity.test(oldPassword) == false) {
            return false;
        }
        Salt salt = Salt.generate();
        entity.hash = PasswordHash.hash(newPassword, salt);
        entity.salt = salt;
        entity.hashAlgorithm = HashAlgorithm.SHA512;
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
        if (entity.test(password) == false) {
            return false;
        }
        userProvider.set(account.id);
        return true;
    }

    public void signout() {
        userProvider.clear();
    }
}
