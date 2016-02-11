package sealion.session;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.GrantDao;
import sealion.domain.AccountRole;
import sealion.domain.Key;
import sealion.entity.Account;

@RequestScoped
public class UserImpl implements User {

    @Inject
    private SessionKey sessionKey;
    @Inject
    private AccountDao accountDao;
    @Inject
    private GrantDao grantDao;

    private boolean signedIn;
    private Account account;
    private List<AccountRole> accountRoles;

    @PostConstruct
    public void init() {
        Optional<Key<Account>> opt = sessionKey.get();
        signedIn = opt.isPresent();
        account = opt.map(accountDao::selectById).orElseGet(Optional::empty).orElse(null);
        accountRoles = opt.map(grantDao::selectByAccount).orElseGet(Collections::emptyList).stream()
                .map(a -> a.role).collect(Collectors.toList());
    }

    @Override
    public boolean isSignedIn() {
        return signedIn;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public List<AccountRole> getAccountRoles() {
        return accountRoles;
    }
}
