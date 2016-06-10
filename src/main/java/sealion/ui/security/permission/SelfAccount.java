package sealion.ui.security.permission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

import sealion.domain.Key;
import sealion.session.User;
import sealion.ui.security.Permission;

@ApplicationScoped
public class SelfAccount implements Permission {

    @Inject
    private User user;

    @Override
    public boolean test(ContainerRequestContext context) {
        String id = context.getUriInfo().getPathParameters().getFirst("id");
        return user.isSignedIn() && user.getAccount().id.equals(Key.valueOf(id));
    }
}
