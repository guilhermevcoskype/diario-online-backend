package com.gui.diarioOnline.controller;


import com.gui.diarioOnline.business.service.ReviewService;
import com.gui.diarioOnline.business.service.TokenService;
import com.gui.diarioOnline.controller.dto.UserRequestLoginDTO;
import com.gui.diarioOnline.controller.dto.UserResponseDTO;
import com.gui.diarioOnline.controller.dto.UserResponseLoginDTO;
import com.gui.diarioOnline.controller.mapper.Mapper;
import com.gui.diarioOnline.infra.entity.Review;
import com.gui.diarioOnline.infra.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private Mapper mapper;

    @PostMapping
    public ResponseEntity<UserResponseLoginDTO> efetuarLogin(@RequestBody @Valid UserRequestLoginDTO userRequestLoginDTO) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userRequestLoginDTO.email(), userRequestLoginDTO.password());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(user);

        List<Review> reviews = reviewService.getReviewFromUser(user.getEmail());

        return ResponseEntity.ok(new UserResponseLoginDTO(
                new UserResponseDTO(
                        user.getName(),
                        user.getEmail(),
                        reviews.stream().map(review -> {
                            return mapper.createMediaDTOFromReview(review);
                        }).toList(),
                        user.getRoles()
                ),
                token
        ));

    }
}