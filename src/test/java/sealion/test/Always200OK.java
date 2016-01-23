package sealion.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class Always200OK extends AbstractBinder {

    @Override
    protected void configure() {
        bind(InterceptionServiceImpl.class).to(InterceptionService.class).in(Singleton.class);
    }

    private static class InterceptionServiceImpl implements InterceptionService {

        @Override
        public Filter getDescriptorFilter() {
            return a -> true;
        }

        @Override
        public List<MethodInterceptor> getMethodInterceptors(Method method) {
            if (Arrays.stream(method.getAnnotations()).map(a -> a.annotationType())
                    .anyMatch(a -> a.isAnnotationPresent(HttpMethod.class))) {
                return Collections.singletonList(a -> Response.ok("Valid").build());
            }
            return Collections.emptyList();
        }

        @Override
        public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> constructor) {
            return Collections.emptyList();
        }
    }
}
