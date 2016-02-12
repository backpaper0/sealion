package sealion.service;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Stereotype;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
@Stereotype
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
}