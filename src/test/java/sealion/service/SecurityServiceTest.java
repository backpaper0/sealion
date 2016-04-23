package sealion.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import sealion.dao.AccountDao;
import sealion.dao.PasswordDao;
import sealion.domain.EmailAddress;
import sealion.domain.HashAlgorithm;
import sealion.domain.Key;
import sealion.domain.PasswordHash;
import sealion.entity.Account;
import sealion.entity.Password;
import sealion.event.SignedInEvent;
import sealion.session.UserProvider;

public class SecurityServiceTest {
    private AccountDao accountDao = Mockito.mock(AccountDao.class);
    private PasswordDao passwordDao = Mockito.mock(PasswordDao.class);
    private UserProvider userProvider = Mockito.mock(UserProvider.class);
    private Event<SignedInEvent> event = Mockito.mock(Event.class);

    @Inject
    private SecurityService service;

    @Test
    public void signin_success() throws Exception {
        Account account = new Account();
        account.id = new Key<>(1L);
        Mockito.when(accountDao.selectByEmail(Mockito.any())).thenReturn(Optional.of(account));

        Password password = new Password();
        password.hashAlgorithm = HashAlgorithm.PLAIN;
        password.hash = new PasswordHash("secret");
        Mockito.when(passwordDao.selectByAccount(Mockito.any())).thenReturn(Optional.of(password));

        EmailAddress email = new EmailAddress("foo@example.com");
        boolean signedin = service.signin(email, "secret");
        assertThat(signedin).isTrue();
    }

    @Test
    public void signin_failure_empty_account() throws Exception {
        Account account = new Account();
        account.id = new Key<>(1L);
        Mockito.when(accountDao.selectByEmail(Mockito.any())).thenReturn(Optional.empty());

        EmailAddress email = new EmailAddress("foo@example.com");
        boolean signedin = service.signin(email, "secret");
        assertThat(signedin).isFalse();
    }

    @Test
    public void signin_failure_empty_password() throws Exception {
        Account account = new Account();
        account.id = new Key<>(1L);
        Mockito.when(accountDao.selectByEmail(Mockito.any())).thenReturn(Optional.of(account));

        Mockito.when(passwordDao.selectByAccount(Mockito.any())).thenReturn(Optional.empty());

        EmailAddress email = new EmailAddress("foo@example.com");

        try {
            service.signin(email, "secret");
            fail();
        } catch (NoSuchElementException expected) {
        }
    }

    @Test
    public void signin_failure_password_mistake() throws Exception {
        Account account = new Account();
        account.id = new Key<>(1L);
        Mockito.when(accountDao.selectByEmail(Mockito.any())).thenReturn(Optional.of(account));

        Password password = new Password();
        password.hashAlgorithm = HashAlgorithm.PLAIN;
        password.hash = new PasswordHash("secret");
        Mockito.when(passwordDao.selectByAccount(Mockito.any())).thenReturn(Optional.of(password));

        EmailAddress email = new EmailAddress("foo@example.com");
        boolean signedin = service.signin(email, "mistake");
        assertThat(signedin).isFalse();
    }

    @Test
    public void update_success() throws Exception {
        Password password = new Password();
        password.hash = new PasswordHash("secret");
        password.hashAlgorithm = HashAlgorithm.PLAIN;
        Mockito.when(passwordDao.selectByAccount(Mockito.any())).thenReturn(Optional.of(password));

        Key<Account> account = new Key<>(1L);
        boolean updated = service.update(account, "secret", "new");
        assertThat(updated).isTrue();
    }

    @Test
    public void update_failure_password_not_found() throws Exception {
        Mockito.when(passwordDao.selectByAccount(Mockito.any())).thenReturn(Optional.empty());

        Key<Account> account = new Key<>(1L);
        boolean updated = service.update(account, "secret", "new");
        assertThat(updated).isFalse();
    }

    @Test
    public void update_failure_mistake_old_password() throws Exception {
        Password password = new Password();
        password.hash = new PasswordHash("secret");
        password.hashAlgorithm = HashAlgorithm.PLAIN;
        Mockito.when(passwordDao.selectByAccount(Mockito.any())).thenReturn(Optional.of(password));

        Key<Account> account = new Key<>(1L);
        boolean updated = service.update(account, "mistake", "new");
        assertThat(updated).isFalse();
    }

    @Before
    public void setUp() throws Exception {
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        ServiceLocatorUtilities.bind(locator, new AbstractBinder() {

            @Override
            protected void configure() {
                bind(SecurityService.class).to(SecurityService.class);
                bind(accountDao).to(AccountDao.class);
                bind(passwordDao).to(PasswordDao.class);
                bind(userProvider).to(UserProvider.class);
                bind(event).to(new TypeLiteral<Event<SignedInEvent>>() {
                });
            }
        });
        locator.inject(this);
    }
}
