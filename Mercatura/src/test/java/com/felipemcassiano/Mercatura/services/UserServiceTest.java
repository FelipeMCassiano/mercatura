package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.dtos.UserDTO;
import com.felipemcassiano.Mercatura.infra.exceptions.EntityConflictException;
import com.felipemcassiano.Mercatura.models.user.UserRole;
import com.felipemcassiano.Mercatura.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName("Should create user successfully when everything is OK")
    void saveCase1() {
        UserDTO userDTO = new UserDTO("test@gmail.com", "1234");

        Mockito.when(userRepository.findByEmail(userDTO.email())).thenReturn(null);
        Mockito.when(passwordEncoder.encode(userDTO.password())).thenReturn(userDTO.password());

        Assertions.assertDoesNotThrow(() -> userService.save(userDTO));
    }

    @Test
    @DisplayName("Should fail when user with email already exists")
    void saveCase2() {
        var user = new com.felipemcassiano.Mercatura.models.user.User("test@gmail", "1234", UserRole.USER);
        UserDTO userDTO = new UserDTO("test@gmail", "4321");

        Mockito.when(userRepository.findByEmail(userDTO.email())).thenReturn(user);

        Assertions.assertThrowsExactly(EntityConflictException.class, () -> userService.save(userDTO));
    }
}