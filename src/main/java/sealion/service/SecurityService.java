package sealion.service;

import java.util.Optional;

import javax.enterprise.event.Event;
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
import sealion.event.SignedInEvent;
import sealion.session.UserProvider;

@Service
public class SecurityService {

    @Inject
    private AccountDao accountDao;
    @Inject
    private PasswordDao passwordDao;
    @Inject
    private UserProvider userProvider;
    @Inject
    private Event<SignedInEvent> signedInEvent;

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
        return passwordDao.selectByAccount(account).filter(entity -> entity.test(oldPassword))
                .map(entity -> {
                    Salt salt = Salt.generate();
                    entity.hash = PasswordHash.hash(newPassword, salt);
                    entity.salt = salt;
                    entity.hashAlgorithm = HashAlgorithm.SHA512;
                    passwordDao.update(entity);
                    return true;
                }).orElse(false);
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
        signedInEvent.fire(new SignedInEvent());
        return true;
    }

    public void signout() {
        userProvider.clear();
    }
}
