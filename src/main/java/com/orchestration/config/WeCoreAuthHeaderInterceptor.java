package com.orchestration.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class WeCoreAuthHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        if (template.headers().containsKey("Authorization")) {
            return;
        }
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return;
        }
        String auth = attrs.getRequest().getHeader("Authorization");
        if (auth == null || auth.isBlank()) {
            return;
        }
        auth = auth.trim();
        if (!auth.regionMatches(true, 0, "Bearer ", 0, 7)) {
            auth = "Bearer " + auth;
        }
        template.header("Authorization", auth);
    }
}
