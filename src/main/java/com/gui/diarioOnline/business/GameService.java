package com.gui.diarioOnline.business;

import com.gui.diarioOnline.controller.dto.SaveGameRequest;
import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.exception.MediaAlreadyExistsException;
import com.gui.diarioOnline.infra.exception.UserNotFoundException;
import com.gui.diarioOnline.infra.model.Game;
import com.gui.diarioOnline.infra.model.Media;
import com.gui.diarioOnline.infra.repository.MediaRepository;
import com.gui.diarioOnline.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class GameService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Transactional
    public void saveGameUser(SaveGameRequest saveGameRequest){
        User user = userRepository.findByEmail(saveGameRequest.email()).orElseThrow(UserNotFoundException::new);
        if (user.getMedia() == null) {
            user.setMedia(new ArrayList<>());
        }

        boolean alreadyExists = user.getMedia().stream()
                .anyMatch(mediaItem -> mediaItem instanceof Game && ((Game) mediaItem).getGameId().equals(saveGameRequest.toModel().getGameId()));

        if (alreadyExists) {
            throw new MediaAlreadyExistsException("O jogo com ID " + saveGameRequest.toModel().getGameId() + " já foi adicionado a este usuário.");
        }

        Media savedMedia = mediaRepository.save(saveGameRequest.toModel());
        user.getMedia().add(savedMedia);

        userRepository.save(user);

    }
}