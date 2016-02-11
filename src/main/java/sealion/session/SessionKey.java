package sealion.session;

import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.SessionScoped;

import sealion.domain.Key;
import sealion.entity.Account;

@SessionScoped
public class SessionKey implements Serializable {

    private Long id;

    public void set(Key<Account> id) {
        this.id = id.getValue();
    }

    public Optional<Key<Account>> get() {
        return Optional.ofNullable(id).map(Key::new);
    }
}
