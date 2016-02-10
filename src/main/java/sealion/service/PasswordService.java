package sealion.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.PasswordDao;
import sealion.domain.Key;
import sealion.domain.PasswordHash;
import sealion.entity.Account;
import sealion.entity.Password;

@ApplicationScoped
@Transactional
public class PasswordService {

    @Inject
    private PasswordDao passwordDao;

    public boolean update(Key<Account> id, String oldPassword, String newPassword) {
        Password entity = passwordDao.selectByAccount(id).get();
        if (entity.hash.getValue().equals(oldPassword) == false) {
            return false;
        }
        entity.hash = new PasswordHash(newPassword);
        passwordDao.update(entity);
        return true;
    }
}
