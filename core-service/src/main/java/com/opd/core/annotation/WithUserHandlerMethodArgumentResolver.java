package com.opd.core.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.opd.core.service.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class WithUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(WithUser.class) && parameter.getParameterType().equals(UserInfo.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		UserInfo userInfo = new UserInfo();

		Claims claims = jwtService.getClaims((HttpServletRequest) webRequest.getNativeRequest());
		if (claims == null)
			return userInfo;

		userInfo.setPrincipal(claims.getSubject());
		return userInfo;
	}

}