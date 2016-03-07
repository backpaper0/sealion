package sealion.session;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private String csrfToken;

    @PostConstruct
    public void init() {
        idRef = new AtomicReference<>();
        try {
            byte[] bs = new byte[20];
            SecureRandom.getInstanceStrong().nextBytes(bs);
            csrfToken = IntStream.range(0, bs.length)
                    .mapToObj(i -> String.format("%02x", bs[i] & 0xff))
                    .collect(Collectors.joining());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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

    public boolean validateCsrfToken(String csrfToken) {
        return Objects.equals(this.csrfToken, csrfToken);
    }

    @Produces
    @RequestScoped
    public User user() {
        Long id = idRef.get();
        if (id == null) {
            return new UserImpl(false, null, Collections.emptyList(), csrfToken);
        }
        Key<Account> accountId = new Key<>(id);
        Account account = accountDao.selectById(accountId).orElse(null);
        List<AccountRole> accountRoles = grantDao.selectByAccount(accountId).stream()
                .map(a -> a.role).collect(Collectors.toList());
        return new UserImpl(true, account, accountRoles, csrfToken);
    }
}
