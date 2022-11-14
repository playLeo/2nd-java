package com.starters.starters_midterm.config;


import com.starters.starters_midterm.interceptor.AdminInterceptor;
import com.starters.starters_midterm.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/user/**","/user")
                .excludePathPatterns("/admin/**","/admin");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**","/admin")
                .excludePathPatterns("/user/**","/user");
    }

}
