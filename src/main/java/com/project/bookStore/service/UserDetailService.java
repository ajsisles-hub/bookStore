package com.project.bookStore.service;


import com.project.bookStore.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    /**
     * description: Function used by spring security for loading user from db
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     *
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userByEmail = userService.getUserByEmail(username);
        return new User(userByEmail.getEmail(), userByEmail.getPassword(), new ArrayList<>());
    }
}
