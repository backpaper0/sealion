package sealion.ui.security.permission;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;

import sealion.ui.security.Permission;

@ApplicationScoped
public class AllowAll implements Permission {

    @Override
    public boolean test(ContainerRequestContext context) {
        return true;
    }
}
