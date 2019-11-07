package com.example.demo.authorization;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "JwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userMapper.selectByUserName(username);

        if (users.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No user found with userphone '%s'.", username));
        } else {
            return JwtUserFactory.create(users.get(0));
        }
    }
}
