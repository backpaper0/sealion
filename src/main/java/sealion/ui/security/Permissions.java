package sealion.ui.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sealion.domain.AccountRole;
import sealion.ui.security.permission.DenyAll;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissions {

    AccountRole[] roles() default {};

    Class<? extends Permission> value() default DenyAll.class;
}
