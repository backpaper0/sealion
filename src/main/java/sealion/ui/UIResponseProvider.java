package sealion.ui;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class UIResponseProvider implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {

        Method resourceMethod = resourceInfo.getResourceMethod();
        if (resourceMethod.getReturnType() != Optional.class) {
            return;
        }

        Type returnType = resourceMethod.getGenericReturnType();
        if ((returnType instanceof ParameterizedType) == false) {
            return;
        }

        ParameterizedType pt = (ParameterizedType) returnType;
        if (pt.getActualTypeArguments()[0] != UIResponse.class) {
            return;
        }

        context.register(new ContainerResponseFilter() {

            @Override
            public void filter(ContainerRequestContext requestContext,
                    ContainerResponseContext responseContext) throws IOException {
                Optional<?> opt = (Optional<?>) responseContext.getEntity();
                responseContext.setEntity(opt.orElse(null));
                responseContext.setStatusInfo(
                        opt.map(a -> responseContext.getStatusInfo()).orElse(Status.NOT_FOUND));
            }
        });
    }
}
