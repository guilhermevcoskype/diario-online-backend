package com.gui.diarioOnline.controller;


import com.gui.diarioOnline.business.service.TokenService;
import com.gui.diarioOnline.controller.dto.UserRequestLogin;
import com.gui.diarioOnline.controller.dto.UserResponse;
import com.gui.diarioOnline.controller.dto.UserResponseLogin;
import com.gui.diarioOnline.infra.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<UserResponseLogin> efetuarLogin(@RequestBody @Valid UserRequestLogin userRequestLogin) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userRequestLogin.email(), userRequestLogin.password());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new UserResponseLogin(
                new UserResponse(
                        user.getName(),
                        user.getEmail(),
                        user.getMedia(),
                        user.getRoles()
                ),
                token
        ));

    }
}