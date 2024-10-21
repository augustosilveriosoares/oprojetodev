package com.opd.core.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.opd.core.annotation.WithUserHandlerMethodArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private WithUserHandlerMethodArgumentResolver withUserHandlerMethodArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(withUserHandlerMethodArgumentResolver);
	}

}