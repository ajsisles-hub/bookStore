package com.project.bookStore.service;

import com.project.bookStore.dto.UserDto;
import com.project.bookStore.model.User;
import com.project.bookStore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    public void shouldReturnUserIdWhenCalledWithUserData() {
        UUID id = UUID.randomUUID();

        when(userRepository.saveAndFlush(any())).thenReturn(getUser(id));
        when(modelMapper.map(any(), any())).thenReturn(getUser(id));

        UUID uuid = userService.addUser(getUserDTO());

        assertThat(uuid).isNotNull();
        assertThat(uuid).isEqualTo(id);

    }

    @Test
    public void shouldReturnUserWhenEmailExists() {
        UUID id = UUID.randomUUID();
        when(userRepository.findByEmail(anyString())).thenReturn(getUser(id));
        when(modelMapper.map(any(), any())).thenReturn(getUserDTO());

        UserDto email = userService.getUserByEmail("user@gmail.com");
        assertThat(email).isNotNull();
        assertThat(email.getName()).isEqualTo("user");
    }

    @Test
    public void shouldThrowErrorUserWhenEmailNotExists() {
        UUID id = UUID.randomUUID();
        when(userRepository.findByEmail(anyString())).thenThrow(new RuntimeException());

    assertThatThrownBy(()-> userService.getUserByEmail("email")).isInstanceOf(RuntimeException.class);
    }

    private UserDto getUserDTO() {
        return UserDto.builder()
                .id(UUID.randomUUID())
                .name("user")
                .email("user@gmail.com")
                .password("password")
                .build();
    }

    private User getUser(UUID uuid) {
        return User.builder()
                .id(uuid)
                .name("user")
                .email("user@gmail.com")
                .password("password")
                .build();
    }

}