package com.iniestar.mark1.config;

import com.iniestar.mark1.config.handler.CustomHttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Redirect root url pattern
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/").setViewName("login");
    }

    /**
     * Http Interceptor
     */
    @Autowired
    CustomHttpInterceptor HandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(HandlerInterceptor)
                .addPathPatterns("/api/**");
    }
}
