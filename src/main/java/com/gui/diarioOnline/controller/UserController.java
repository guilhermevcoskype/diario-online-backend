package com.gui.diarioOnline.controller;

import com.gui.diarioOnline.business.service.UserService;
import com.gui.diarioOnline.controller.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") @NotNull @Positive String email) {
        userService.deleteUser(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateMediaUser(@RequestBody @Valid UserMediaUpdateRequest userMediaUpdateRequest) {
        return userService.updateMediaUser(userMediaUpdateRequest);
    }

    @PostMapping("/userMedia")
    @ResponseStatus(HttpStatus.OK)
    public List<MediaListUpdateResponse> getMediaUser(@RequestParam String emailRequest) {
        return userService.getMediaUser(emailRequest);
    }

}
