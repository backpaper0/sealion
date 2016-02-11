package sealion.session;

import java.util.List;

import sealion.domain.AccountRole;
import sealion.entity.Account;

public interface User {

    boolean isSignedIn();

    Account getAccount();

    List<AccountRole> getAccountRoles();
}
