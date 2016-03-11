package sealion.session;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import sealion.event.SignedInEvent;

@RequestScoped
public class SessionIdChanger {

    @Inject
    private HttpServletRequest request;

    public void signedIn(@Observes SignedInEvent event) {
        request.changeSessionId();
    }
}
