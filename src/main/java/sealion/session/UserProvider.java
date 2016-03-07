package sealion.session;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.GrantDao;
import sealion.domain.AccountRole;
import sealion.domain.Key;
import sealion.entity.Account;

@SessionScoped
public class UserProvider implements Serializable {

    @Inject
    private transient AccountDao accountDao;
    @Inject
    private transient GrantDao grantDao;

    private AtomicReference<Long> idRef;

    @PostConstruct
    public void init() {
        idRef = new AtomicReference<>();
    }

    public void set(Key<Account> id) {
        idRef.set(id.getValue());
    }

    public Optional<Key<Account>> get() {
        return Optional.ofNullable(idRef.get()).map(Key::new);
    }

    public void clear() {
        idRef.set(null);
    }

    @Produces
    @RequestScoped
    public User user() {
        Long id = idRef.get();
        if (id == null) {
            return new UserImpl(false, null, Collections.emptyList());
        }
        Key<Account> accountId = new Key<>(id);
        Account account = accountDao.selectById(accountId).orElse(null);
        List<AccountRole> accountRoles = grantDao.selectByAccount(accountId).stream()
                .map(a -> a.role).collect(Collectors.toList());
        return new UserImpl(true, account, accountRoles);
    }
}
