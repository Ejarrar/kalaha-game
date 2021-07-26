package com.assignment.kalahagamedemo.controller.rest;

import com.assignment.kalahagamedemo.model.Game;
import com.assignment.kalahagamedemo.model.request.MakeMoveRequest;
import com.assignment.kalahagamedemo.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class KalahaGameController {

    private final GameService gameService;

    public KalahaGameController(GameService gameService) {
        this.gameService = gameService;
    }


    @GetMapping("/kalaha")
    public ResponseEntity<Game> getGameService() {
        return ResponseEntity.ok(gameService.getGame());
    }

    @PostMapping("/kalaha/play")
    public ResponseEntity<Game> performMove(@RequestBody MakeMoveRequest request) {
        return ResponseEntity.ok(gameService.play(request));
    }

}
