package com.test.springcloud.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * JWT token管理
 *
 */
@Component
public class JwtTokenManager {
	
    private static final String AUTHORITIES_KEY = "auth";

    private static final String SECRET_KEY = "SecretKey012345678901234567890123456789012345678901234567890123456789";

    /**
     * Token 有效时间(ms)
     */
    private static final long TOKEN_VALIDITY_IN_MILLISECONDS = 1000 * 60 * 60 * 12L;
    
    private static final byte[] SECRET_KEY_BYTES = Decoders.BASE64.decode(SECRET_KEY);
    
    /**
     * 创建 token.
     *
     * @param authentication auth info
     * @return token
     */
    public String createToken(Authentication authentication) {
        return createToken(authentication.getName());
    }


    /**
     * 创建 token
     *
     * @param userName
     * @return token
     */
    public String createToken(String userName) {
        long now = (new Date()).getTime();
        Date validity;
        validity = new Date(now + TOKEN_VALIDITY_IN_MILLISECONDS);
        Claims claims = Jwts.claims().setSubject(userName);
        return Jwts.builder().setClaims(claims).setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY_BYTES), SignatureAlgorithm.HS256).compact();
    }

    /**
     * 获取 auth信息
     *
     * @param token token
     * @return auth info
     */
    public Authentication getAuthentication(String token) {
    	Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY_BYTES).build()
                .parseClaimsJws(token).getBody();
        
        List<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES_KEY));
        
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * 验证 token
     *
     * @param token token
     */
    public void validateToken(String token) {
    	Jwts.parserBuilder().setSigningKey(SECRET_KEY_BYTES).build().parseClaimsJws(token);
    }
    
    public static void main(String[] args) {
    	JwtTokenManager jtm = new JwtTokenManager();
    	String token = jtm.createToken("lfp");
    	System.out.println(token);
    }

}
