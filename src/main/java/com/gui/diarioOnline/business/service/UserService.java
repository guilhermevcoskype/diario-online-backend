package com.gui.diarioOnline.business.service;

import com.gui.diarioOnline.controller.dto.UserRequest;
import com.gui.diarioOnline.controller.dto.UserResponse;
import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.exception.EmailAlreadyUsedException;
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

    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream().map(user -> new UserResponse(
                        user.getName(),
                        user.getEmail(),
                        user.getMedia(),
                        user.getRoles()
                ))
                .toList();
    }

    @Transactional
    public void deleteUser(String email){
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        User user = userRequest.toModel();
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        try {
            return userRepository.save(user).toResponse();
        } catch (DuplicateKeyException e) {
            throw new EmailAlreadyUsedException("O e-mail fornecido já está em uso.");
        }
    }
}
