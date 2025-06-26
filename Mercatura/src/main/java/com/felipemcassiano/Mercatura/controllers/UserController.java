package com.felipemcassiano.Mercatura.controllers;

import com.felipemcassiano.Mercatura.dtos.UserDTO;
import com.felipemcassiano.Mercatura.dtos.UserLoginResponseDTO;
import com.felipemcassiano.Mercatura.models.user.User;
import com.felipemcassiano.Mercatura.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserDTO request) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = userService.generateUserToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new UserLoginResponseDTO(token));
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody UserDTO request) {
        userService.save(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("register-admin")
    public ResponseEntity<Void> registerAdmin(@RequestBody UserDTO request) {
        userService.saveAdmin(request);
        return ResponseEntity.ok().build();
    }

}
