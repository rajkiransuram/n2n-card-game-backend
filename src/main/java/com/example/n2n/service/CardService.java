package com.example.n2n.service;

import com.example.n2n.entity.Cards;
import com.example.n2n.model.CardsRequest;
import com.example.n2n.model.CardsResponse;
import com.example.n2n.model.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CardService {
    void saveCards(CardsRequest cardsRequest) throws IOException;
    CardsResponse getCards();

    CardsResponse updateCards(CardsResponse cardsRequest);
    Player cardDistribution(CardsRequest cardsRequest);

    String findWinner(Map<String, List<String>> players);

}
