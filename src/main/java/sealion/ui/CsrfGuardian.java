package sealion.ui;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

import sealion.session.UserProvider;

@Provider
@ApplicationScoped
public class CsrfGuardian implements ReaderInterceptor {

    @Inject
    private UserProvider userProvider;

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context)
            throws IOException, WebApplicationException {
        Object entity = context.proceed();
        MediaType mediaType = context.getMediaType();
        if (mediaType != null
                && mediaType.isCompatible(MediaType.APPLICATION_FORM_URLENCODED_TYPE)) {
            Form form = (Form) entity;
            String csrfToken = form.asMap().getFirst("csrfToken");
            if (userProvider.validateCsrfToken(csrfToken) == false) {
                throw new BadRequestException();
            }
        }
        return entity;
    }
}
