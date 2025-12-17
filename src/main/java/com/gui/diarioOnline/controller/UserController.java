package com.gui.diarioOnline.controller;

import com.gui.diarioOnline.business.service.ReviewService;
import com.gui.diarioOnline.business.service.UserService;
import com.gui.diarioOnline.controller.dto.*;
import com.gui.diarioOnline.controller.mapper.Mapper;
import com.gui.diarioOnline.infra.entity.Review;
import com.gui.diarioOnline.infra.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private Mapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> getUsers() {
        List<User> listUser = userService.getUsers();
        return !listUser.isEmpty() ? listUser.stream().map(user -> mapper.userToResponse(user))
                .toList() : new ArrayList<>();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") @NotNull @Email String email) {
        userService.deleteUser(email);
    }

    @DeleteMapping("/deleteMediaFromUser")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMediaFromUser(@RequestBody @Valid DeleteMediaRequestDTO deleteMediaRequestDTO) {
        reviewService.deleteGameFromUser(deleteMediaRequestDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return mapper.userToResponse(userService.createUser(userRequestDTO));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Review updateMediaUser(@RequestBody @Valid UserMediaUpdateRequestDTO userMediaUpdateRequestDTO) {
        return reviewService.updateMediaOnUser(userMediaUpdateRequestDTO);
    }

    @PostMapping("/saveMediaOnUser")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO saveMediaOnUser(@RequestBody @Valid SaveMediaRequestDTO saveMediaRequestDTO) {
        return mapper.userToResponse(reviewService.saveMediaOnUser(saveMediaRequestDTO).getUser());
    }

    @PostMapping("/mediaFromUser")
    @ResponseStatus(HttpStatus.OK)
    public List<MediaListUpdateResponseDTO> getMediaFromUser(@RequestParam String emailRequest) {
        return reviewService.getMediaFromUser(emailRequest).stream().map(media -> mapper.MediaToResponse(media, emailRequest))
                .toList();
    }

}
