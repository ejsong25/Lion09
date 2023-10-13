package com.lion09;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lion09.login.LoginCheckInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())
				.order(1)
				.addPathPatterns("/**")
				.excludePathPatterns("/", "/login", "/logout",
						"/user/signup", "/css/**", "/js/**", "/img/**");
	}
}
