package dev.onurcakir.ecommerce.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;

public class AuthKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}