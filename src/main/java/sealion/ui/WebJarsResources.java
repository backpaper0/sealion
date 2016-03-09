package sealion.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@ApplicationScoped
@Provider
@PreMatching
public class WebJarsResources implements ContainerRequestFilter {

    private Map<String, MediaType> extension;

    @PostConstruct
    public void init() {
        Map<String, MediaType> extension = new HashMap<>();
        extension.put(".js", MediaType.valueOf("application/javascript"));
        extension.put(".css", MediaType.valueOf("text/css"));
        this.extension = extension;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("webjars/")) {
            InputStream in = getClass().getResourceAsStream("/META-INF/resources/" + path);
            if (in != null) {
                MediaType mediaType = extension.get(path.substring(path.lastIndexOf('.')));
                Response response = Response.ok(in, mediaType).build();
                requestContext.abortWith(response);
            }
        }
    }
}
