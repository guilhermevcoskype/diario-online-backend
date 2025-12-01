package com.gui.diarioOnline.controller;

import com.gui.diarioOnline.business.GameService;
import com.gui.diarioOnline.business.IGDBService;
import com.gui.diarioOnline.business.UserService;
import com.gui.diarioOnline.controller.dto.DetailedGameResponse;
import com.gui.diarioOnline.controller.dto.SaveGameRequest;
import com.gui.diarioOnline.infra.model.Game;
import com.gui.diarioOnline.infra.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gamelist")
public class GamelistController {

    @Autowired
    private IGDBService igbdService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @PostMapping("/games")
    public ResponseEntity<List<Media>> gamesList(@RequestBody String gameName) {
        List<DetailedGameResponse> list = igbdService.consumeGamesList(gameName);
        List<Media> listMedia = new ArrayList<>();
        list.forEach(dataildGame -> {
            Game game = new Game();
            game.setGameId(dataildGame.id());
            game.setName(dataildGame.name());
            game.setSummary(dataildGame.summary());
            game.setCover(dataildGame.coverUrl());
            listMedia.add(game);
        });

        return ResponseEntity.ok(listMedia);
    }

    @PostMapping("/savegame")
    @ResponseStatus(HttpStatus.OK)

    public void savegame(@RequestBody SaveGameRequest saveGameRequest) {
        gameService.saveGameUser(saveGameRequest);
    }
}