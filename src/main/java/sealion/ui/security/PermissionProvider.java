package sealion.ui.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import sealion.domain.AccountRole;
import sealion.session.User;
import sealion.ui.RootResource;

@ApplicationScoped
@Provider
public class PermissionProvider implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {

        if (resourceInfo.getResourceClass().getName()
                .startsWith(RootResource.class.getPackage().getName() + ".") == false) {
            return;
        }

        Permissions permissions = resourceInfo.getResourceMethod().getAnnotation(Permissions.class);
        if (permissions == null) {
            permissions = resourceInfo.getResourceClass().getAnnotation(Permissions.class);
        }
        if (permissions == null) {
            //TODO
            Logger.getLogger(PermissionProvider.class.getName())
                    .warning(String.format("%s not annotated @Permissions",
                            resourceInfo.getResourceMethod().toGenericString()));
            return;
        }
        context.register(new PermissionTester(permissions));
    }

    private static class PermissionTester implements ContainerRequestFilter {

        private final Permissions permissions;

        public PermissionTester(Permissions permissions) {
            this.permissions = permissions;
        }

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
            Instance<Object> instance = CDI.current();
            User user = instance.select(User.class).get();

            List<AccountRole> roles = user.getAccountRoles();
            if (Arrays.stream(permissions.roles()).anyMatch(roles::contains)) {
                return;
            }

            Permission permission = instance.select(permissions.value()).get();
            if (permission.test(requestContext)) {
                return;
            }

            throw new ForbiddenException();
        }
    }
}
