package sealion.ui.security;

import javax.ws.rs.container.ContainerRequestContext;

public interface Permission {
    boolean test(ContainerRequestContext context);
}
