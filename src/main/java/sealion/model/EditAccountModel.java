package sealion.model;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import sealion.dao.AccountDao;
import sealion.dao.GrantDao;
import sealion.domain.AccountRole;
import sealion.domain.Key;
import sealion.entity.Account;
import sealion.entity.Grant;

public class EditAccountModel {
    public Account account;
    public GrantContainer grant;
    public List<AccountRole> roles;

    public static class GrantContainer {
        private List<Grant> values;

        public GrantContainer(List<Grant> values) {
            this.values = values;
        }

        public boolean containsRole(AccountRole role) {
            return values.stream().anyMatch(grant -> grant.role == role);
        }
    }

    @RequestScoped
    public static class Builder {
        @Inject
        private AccountDao accountDao;
        @Inject
        private GrantDao grantDao;

        public EditAccountModel build(Key<Account> id) {
            EditAccountModel model = new EditAccountModel();
            model.account = accountDao.selectById(id).get();
            model.grant = new GrantContainer(grantDao.selectByAccount(id));
            model.roles = Arrays.asList(AccountRole.values());
            return model;
        }
    }
}
