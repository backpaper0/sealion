package sealion.ui.security.permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

import sealion.session.User;
import sealion.ui.security.Permission;

@ApplicationScoped
public class SignedIn implements Permission {

    @Inject
    private User user;

    @Override
    public boolean test(ContainerRequestContext context) {
        return user.isSignedIn();
    }
}
