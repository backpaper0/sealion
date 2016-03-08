package sealion.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.GrantDao;
import sealion.domain.AccountRole;
import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.domain.Username;
import sealion.entity.Account;
import sealion.entity.Grant;
import sealion.exception.DuplicateEmailException;

@Service
public class AccountService {

    @Inject
    private AccountDao accountDao;
    @Inject
    private GrantDao grantDao;
    @Inject
    private SecurityService securityService;

    public Account create(Username username, EmailAddress email, String password)
            throws DuplicateEmailException {

        if (accountDao.selectByEmail(email).isPresent()) {
            throw new DuplicateEmailException();
        }

        Account entity = new Account();
        entity.username = username;
        entity.email = email;
        accountDao.insert(entity);

        securityService.create(entity.id, password);

        return entity;
    }

    public void update(Key<Account> id, EmailAddress email, List<AccountRole> roles)
            throws DuplicateEmailException {

        if (accountDao.selectByEmail(email).map(a -> a.id).filter(Predicate.isEqual(id).negate())
                .isPresent()) {
            throw new DuplicateEmailException();
        }

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
