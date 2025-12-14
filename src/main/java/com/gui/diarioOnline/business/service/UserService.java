package com.gui.diarioOnline.business.service;

import com.gui.diarioOnline.controller.dto.UserRequestDTO;
import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.exception.EmailAlreadyUsedException;
import com.gui.diarioOnline.infra.exception.UserNotFoundException;
import com.gui.diarioOnline.infra.repository.MediaRepository;
import com.gui.diarioOnline.infra.repository.ReviewRepository;
import com.gui.diarioOnline.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        String userId = user.getId();

        reviewRepository.deleteByUserId(userId);

        userRepository.deleteByEmail(email);
    }

    @Transactional
    public User createUser(UserRequestDTO userRequestDTO) {
        User user = userRequestDTO.toModel();
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        try {
            return userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new EmailAlreadyUsedException("O e-mail fornecido já está em uso.");
        }
    }

}
