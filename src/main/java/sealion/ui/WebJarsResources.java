package sealion.ui;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@ApplicationScoped
@Provider
@PreMatching
public class WebJarsResources implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("webjars/")) {
            InputStream in = getClass().getResourceAsStream("/META-INF/resources/" + path);
            if (in != null) {
                Response response = Response.ok(in).build();
                requestContext.abortWith(response);
            }
        }
    }
}
