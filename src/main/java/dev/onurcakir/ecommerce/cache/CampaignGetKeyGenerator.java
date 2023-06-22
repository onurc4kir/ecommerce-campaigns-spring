package dev.onurcakir.ecommerce.cache;

import dev.onurcakir.ecommerce.security.service.UserDetailsImpl;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;

public class CampaignGetKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder();
        for(int i = 0; i<params.length;i++){
            key.append(String.format("%d:%s_", i, params[i].toString()));
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String mail = ((UserDetailsImpl) auth.getPrincipal()).getEmail();
            key.append("_");
            key.append(mail);
        }
        return key.toString();
    }
}