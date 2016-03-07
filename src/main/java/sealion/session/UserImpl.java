package sealion.session;

import java.util.List;

import sealion.domain.AccountRole;
import sealion.entity.Account;

public class UserImpl implements User {

    private boolean signedIn;
    private Account account;
    private List<AccountRole> accountRoles;

    public UserImpl(boolean signedIn, Account account, List<AccountRole> accountRoles) {
        this.signedIn = signedIn;
        this.account = account;
        this.accountRoles = accountRoles;
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
