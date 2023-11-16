package com.example.n2n.controller;


import com.example.n2n.model.CardsRequest;
import com.example.n2n.model.CardsResponse;
import com.example.n2n.model.Player;
import com.example.n2n.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:8080/")
public class CardsController {

    @Autowired
    private CardService cardService;

    @GetMapping("cards")
    public ResponseEntity<CardsResponse> getCards() throws IOException {
        CardsResponse cardsList = cardService.getCards();
        return ResponseEntity.ok(cardsList);
    }

    @PostMapping("saveCards")
    public ResponseEntity<String> saveCards(@RequestBody CardsRequest cardsRequest) throws IOException {
       cardService.saveCards(cardsRequest);
        return ResponseEntity.ok("Cards saved Successfully");
    }

    @GetMapping("shuffle")
    public ResponseEntity<CardsResponse> shuffleCards(){
        CardsResponse cardsList = cardService.getCards();
        Collections.shuffle(cardsList.getCards());
        return ResponseEntity.ok(cardsList);
    }

    @PostMapping("distribute")
    public ResponseEntity<Player> distributeCards(@RequestBody CardsRequest cardsRequest){
        Player players = cardService.cardDistribution(cardsRequest);
        return ResponseEntity.ok(players);
    }

    @PostMapping("winner")
    public ResponseEntity<String> findWinner(@RequestBody Player player){
        String winner =  cardService.findWinner(player.getPlayer());
        return ResponseEntity.ok(winner);
    }
}
