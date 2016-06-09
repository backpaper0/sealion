package sealion.service;

import java.util.NoSuchElementException;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.AccountPasswordDao;
import sealion.domain.EmailAddress;
import sealion.domain.HashAlgorithm;
import sealion.domain.Key;
import sealion.domain.PasswordHash;
import sealion.domain.Salt;
import sealion.embeddable.Password;
import sealion.entity.Account;
import sealion.entity.AccountPassword;
import sealion.event.SignedInEvent;
import sealion.session.UserProvider;

@Service
public class SecurityService {

    @Inject
    private AccountDao accountDao;
    @Inject
    private AccountPasswordDao passwordDao;
    @Inject
    private UserProvider userProvider;
    @Inject
    private Event<SignedInEvent> signedInEvent;

    public void create(Key<Account> account, String password) {
        Salt salt = Salt.generate();
        AccountPassword entity = new AccountPassword();
        entity.account = account;
        PasswordHash hash = PasswordHash.hash(password, salt);
        HashAlgorithm hashAlgorithm = HashAlgorithm.SHA512;
        entity.password = new Password(hash, salt, hashAlgorithm);
        passwordDao.insert(entity);
    }

    public boolean update(Key<Account> account, String oldPassword, String newPassword) {
        return passwordDao.selectByAccount(account)
                .filter(entity -> entity.password.test(oldPassword)).map(entity -> {
                    Salt salt = Salt.generate();
                    PasswordHash hash = PasswordHash.hash(newPassword, salt);
                    HashAlgorithm hashAlgorithm = HashAlgorithm.SHA512;
                    entity.password = new Password(hash, salt, hashAlgorithm);
                    passwordDao.update(entity);
                    return true;
                }).orElse(false);
    }

    public boolean signin(EmailAddress email, String password) {
        return accountDao.selectByEmail(email)
                .filter(account -> passwordDao.selectByAccount(account.id)
                        .map(entity -> entity.password.test(password))
                        .orElseThrow(NoSuchElementException::new))
                .map(account -> {
                    userProvider.set(account.id);
                    signedInEvent.fire(new SignedInEvent());
                    return true;
                }).orElse(false);
    }

    public void signout() {
        userProvider.clear();
    }
}
