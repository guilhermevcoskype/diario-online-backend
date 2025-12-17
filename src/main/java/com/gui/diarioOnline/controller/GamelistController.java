package com.gui.diarioOnline.controller;

import com.gui.diarioOnline.business.service.IGDBService;
import com.gui.diarioOnline.business.service.UserService;
import com.gui.diarioOnline.controller.dto.DetailedGameResponseDTO;
import com.gui.diarioOnline.controller.dto.MediaListUpdateResponseDTO;
import com.gui.diarioOnline.controller.mapper.Mapper;
import com.gui.diarioOnline.infra.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gamelist")
@CrossOrigin
public class GamelistController {

    @Autowired
    private IGDBService igbdService;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    @PostMapping("/games")
    public ResponseEntity<List<MediaListUpdateResponseDTO>> gamesList(@RequestParam String gameName, @RequestParam String email) {
        List<DetailedGameResponseDTO> list = igbdService.consumeGamesList(gameName);
        List<MediaListUpdateResponseDTO> listMedia = new ArrayList<>();
        list.forEach(dataildGame -> {
            Game game = new Game();
            game.setBusinessId(dataildGame.id().toString());
            game.setName(dataildGame.name());
            game.setSummary(dataildGame.summary());
            game.setCover(dataildGame.coverUrl().replace("t_thumb", "t_cover_big"));
            listMedia.add(mapper.MediaToResponse(game, email));
        });

        return ResponseEntity.ok(listMedia);
    }
}