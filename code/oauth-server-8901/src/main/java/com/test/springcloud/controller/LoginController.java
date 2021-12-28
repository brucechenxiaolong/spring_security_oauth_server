package com.test.springcloud.controller;

import com.test.springcloud.service.JwtTokenManager;
import com.test.springcloud.util.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * oauth 登录认证主方法
 */
@RestController("auth")
@RequestMapping("/v1/auth")
public class LoginController {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JwtTokenManager jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResult<String> login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");//密码需要加密传递
        password = "ias4WEYIzsXOhZKnqIDv0A==";//888加密后的值

        // 通过用户名和密码创建一个 Authentication 认证对象，实现类为 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        RestResult<String> rr = new RestResult<String>();
        try {
            /**
             * 通过 AuthenticationManager（默认实现为ProviderManager）的authenticate方法验证 Authentication 对象
             * AbstractUserDetailsAuthenticationProvider.authenticate.additionalAuthenticationChecks验证密码
             */
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            //将 Authentication 绑定到 SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //生成Token(***每次token都不同***)
            String token = jwtTokenProvider.createToken(authentication);
            //将Token写入到Http头部
            response.addHeader(AUTHORIZATION_HEADER, "Bearer " + token);
            rr.setCode(200);
            rr.setData("Bearer " + token);
            rr.setMessage("登录认证成功！");
            return rr;
        } catch (BadCredentialsException authentication) {
            rr.setCode(401);
            rr.setMessage("用户名或密码不正确.");
            return rr;
        }
    }

}
