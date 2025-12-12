package com.gui.diarioOnline.business.service;

import com.gui.diarioOnline.controller.dto.*;
import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.exception.EmailAlreadyUsedException;
import com.gui.diarioOnline.infra.exception.UserNotFoundException;
import com.gui.diarioOnline.infra.model.Game;
import com.gui.diarioOnline.infra.model.Media;
import com.gui.diarioOnline.infra.repository.MediaRepository;
import com.gui.diarioOnline.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(user -> new UserResponse(
                        user.getName(),
                        user.getEmail(),
                        user.getMedia(),
                        user.getRoles()
                ))
                .toList();
    }

    public List<MediaListUpdateResponse> getMediaUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new).getMedia().stream().map(
                media -> {
                    return new MediaListUpdateResponse(
                            media.getName(),
                            media.getSummary(),
                            media.getCover(),
                            media.getRating(),
                            media.getComments()
                    );
                }
        ).toList();
    }

    @Transactional
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = userRequest.toModel();
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        try {
            return userRepository.save(user).toResponse();
        } catch (DuplicateKeyException e) {
            throw new EmailAlreadyUsedException("O e-mail fornecido já está em uso.");
        }
    }

    @Transactional
    public UserResponse updateMediaUser(UserMediaUpdateRequest userMediaUpdateRequest) {
        User user = userRepository.findByEmail(userMediaUpdateRequest.email()).orElseThrow(UserNotFoundException::new);
        MediaListUpdateRequest mediaRequest = userMediaUpdateRequest.media();
        Media mediaRecovered = mediaRepository.findById(mediaRequest.gameId()).orElse(null);
        if (Objects.isNull(mediaRecovered)) {
            Game gameToSave = Game.builder().gameId(Long.parseLong(mediaRequest.gameId())).comments(mediaRequest.comments()).rating(mediaRequest.rating()).name(mediaRequest.name()).cover(mediaRequest.cover()).summary(mediaRequest.summary()).build();
            Media mediaToSave = mediaRepository.save(gameToSave);
            user.getMedia().add(mediaToSave);
        }else{
            user.getMedia().add(mediaRecovered);
        }
        try {
            return userRepository.save(user).toResponse();
        } catch (DuplicateKeyException e) {
            throw new EmailAlreadyUsedException("O e-mail fornecido já está em uso.");
        }
    }
}
