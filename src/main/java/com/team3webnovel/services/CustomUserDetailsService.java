package com.team3webnovel.services;

import com.team3webnovel.dao.UserDao;
import com.team3webnovel.mappers.UserMapper;
import com.team3webnovel.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

@Autowired
private UserMapper userMapper;

	@Autowired
	private UserDao userDao;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 데이터베이스에서 사용자 정보를 로드
        UserVo user = userMapper.findUserByUsername(username);
        UserVo userVo = userDao.findUserByUsername(username);
        if (userVo == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        // 다중 권한 설정
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (username.equals("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // 스프링 시큐리티의 UserDetails 객체 반환
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),  // 암호화된 비밀번호 사용
                authorities
        );
    }
}
