package sealion.session;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.builder.SelectBuilder;

import sealion.domain.AccountRole;
import sealion.domain.Key;
import sealion.entity.Account;
import sealion.entity.Grant;

@Dependent
public class MockUserProvider {

    @Inject
    private Config config;

    @Produces
    @RequestScoped
    public User user() {
        Account account = SelectBuilder.newInstance(config)
                .sql("SELECT * FROM Account WHERE username = ").param(String.class, "foo")
                .getEntitySingleResult(Account.class);

        List<AccountRole> accountRoles = SelectBuilder.newInstance(config)
                .sql("SELECT * FROM Grant WHERE account = ").param(Key.class, account.id)
                .getEntityResultList(Grant.class).stream().map(g -> g.role)
                .collect(Collectors.toList());

        return new User() {

            @Override
            public Account getAccount() {
                return account;
            }

            @Override
            public List<AccountRole> getAccountRoles() {
                return accountRoles;
            }
        };
    }
}
