package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.infra.exceptions.EntityConflictException;
import com.felipemcassiano.Mercatura.infra.security.TokenService;
import com.felipemcassiano.Mercatura.models.user.User;
import com.felipemcassiano.Mercatura.models.user.UserDTO;
import com.felipemcassiano.Mercatura.models.user.UserRole;
import com.felipemcassiano.Mercatura.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void save(UserDTO dto) {
        if (userRepository.findByEmail(dto.email()) != null) {
            throw new EntityConflictException("User with email already exists");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        User newUser = new User(dto.email(), encryptedPassword, UserRole.USER);

        userRepository.save(newUser);
    }

    public String generateUserToken(User user) {
        return tokenService.generateToken(user);
    }
}
