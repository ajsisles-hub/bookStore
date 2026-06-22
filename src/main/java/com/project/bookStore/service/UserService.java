package com.project.bookStore.service;

import com.project.bookStore.dto.UserDto;
import com.project.bookStore.model.User;
import com.project.bookStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public UUID addUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(null);

        User createdUser = userRepository.saveAndFlush(user);

        return createdUser.getId();
    }

    public UserDto getUserByEmail(String email) {
        User byEmail = userRepository.findByEmail(email);

        if(Objects.isNull(byEmail)){
            throw new RuntimeException("User not exist" + email);
        }

        return modelMapper.map(byEmail, UserDto.class);
    }

}
