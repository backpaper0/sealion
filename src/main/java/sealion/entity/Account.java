package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import sealion.domain.EmailAddress;
import sealion.domain.Key;
import sealion.domain.Username;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key<Account> id;
    public Username username;
    public EmailAddress email;
}
