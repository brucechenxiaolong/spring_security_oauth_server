package com.test.springcloud.service;

import com.test.springcloud.config.WebSecurityConfig;
import com.test.springcloud.util.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt身份验证
 *
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private static final String TOKEN_PREFIX = "Bearer ";

	private JwtTokenManager tokenProvider;

	public JwtAuthenticationTokenFilter(JwtTokenManager tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String url = request.getRequestURL().toString();
		System.out.println(url);
		String jwt = resolveToken(request);
		if (StringUtils.isNotBlank(jwt) && SecurityContextHolder.getContext().getAuthentication() == null) {
            this.tokenProvider.validateToken(jwt);//可考虑在此catch ValidateException异常
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(WebSecurityConfig.AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        String jwt = request.getParameter("accessToken");//理论上应该分开，此处accessToken应该不带Bearer加空格
        if (StringUtils.isNotBlank(jwt) && jwt.startsWith(TOKEN_PREFIX)) {
            return jwt.substring(TOKEN_PREFIX.length());
        }
        return null;
	}
}
