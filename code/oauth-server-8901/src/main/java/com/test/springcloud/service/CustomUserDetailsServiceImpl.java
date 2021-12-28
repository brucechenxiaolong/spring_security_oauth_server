package com.test.springcloud.service;

import com.test.springcloud.dto.User;
import com.test.springcloud.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 自定义获取用户
 *
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		//TODO CXL-此处应该查询数据库，为了测试直接写死
		User user = new User();
		user.setUsername(userName);
		//888=$2a$10$WBEFsRICjZPXxXGPgnw7CucaHC/iRflj/sr.dGe9cC0C9skaGpcSi
//		user.setPassword(new BCryptPasswordEncoder().encode("888"));//假装数据库已经存了加密的密码,实际情况要从数据库中查询出来，不能少

		//ias4WEYIzsXOhZKnqIDv0A==
		user.setPassword(new BCryptPasswordEncoder().encode("ias4WEYIzsXOhZKnqIDv0A=="));//888加密后的值
		//查询数据库
//		User user = authUserService.findUserByUsername(userName);//定义获取用户信息接口，系统管理去实现

		if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        return new CustomUserDetails(user);
	}

}
