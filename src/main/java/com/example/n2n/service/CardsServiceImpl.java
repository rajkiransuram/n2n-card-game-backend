package com.example.n2n.service;

import com.example.n2n.entity.Cards;
import com.example.n2n.model.*;
import com.example.n2n.repository.CardsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class CardsServiceImpl implements CardService {

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Autowired
    private CardsRepository cardsRepository;

    @Value("classpath:cards.json")
    Resource resource;

    @Override
    public void saveCards(CardsRequest cardsRequest) throws IOException {
       cardsRepository.saveAll(cardsRequest.getCards());
    }

    @Override
    public CardsResponse getCards() {
        ObjectMapper mapper = new ObjectMapper();
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CardsResponse cardsResponse = null;
        try {
            cardsResponse = mapper.readValue(file, CardsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cardsResponse.setCards(cardsResponse.getCards());
        return cardsResponse;
    }

    @Override
    public CardsResponse updateCards(CardsResponse cardsRequest) {
        CardsResponse cardsResponse =  modelMapper.map(cardsRequest,CardsResponse.class);
        return (CardsResponse) cardsRepository.saveAll(cardsResponse.getCards());
    }

    @Override
    public Player cardDistribution(CardsRequest cardsRequest) {
        int numPlayers = 4;
        List<Cards> cardList = cardsRequest.getCards();
        List<String> deck = cardList.stream().map(Cards::getCard).toList();
        List<List<String>> playerHands = distributeCards(deck);
        Player player = new Player();
        Map<String,List<String>> listMap = new LinkedHashMap<>();
        for (int i = 0; i < numPlayers; i++) {
            listMap.put("Player "+(i+1),playerHands.get(i));
            player.setPlayer(listMap);
         //   LOGGER.info("Players -> "+ (i + 1) + ": " + playerHands.get(i));
        }
        return player;
    }

    @Override
    public String findWinner(Map<String, List<String>> playersMap) {
        String winner = "";
        List<CardPlayer> players = new ArrayList<>();
        for(Map.Entry<String, List<String>> map : playersMap.entrySet()){
            CardPlayer cardPlayer = new CardPlayer();
            cardPlayer.setName(map.getKey());
            List<Card> result = Stream.of(map.getValue())
                    .map(x -> {
                        Card u = new Card();
                        u.setSymbol(x.toString());
                        return u;
                    })
                    .toList();
            cardPlayer.setCards(result);
            players.add(cardPlayer);
        }
        return determineWinner(players.get(0),players.get(1),players.get(2),players.get(3)).getName();
    }

    private static CardPlayer determineWinner(CardPlayer... players) {
        CardPlayer winner = players[0];

        for (int i = 1; i < players.length; i++) {
            if (comparePlayers(players[i], winner) > 0) {
                winner = players[i];
            }
        }
        return winner;
    }

    private static int comparePlayers(CardPlayer player1, CardPlayer player2) {
        List<Card> cards1 = player1.getCards();
        List<Card> cards2 = player2.getCards();

        // Sort the cards in descending order
       // Collections.sort(cards1, Collections.reverseOrder());
       // Collections.sort(cards2, Collections.reverseOrder());

        for (int i = 0; i < Math.min(cards1.size(), cards2.size()); i++) {
            int comparison = cards1.get(i).compareTo(cards2.get(i));
            if (comparison != 0) {
                return comparison;
            }
        }
        // If the alphanumeric portions are the same, compare symbols
        return cards1.get(0).getSymbol().compareTo(cards2.get(0).getSymbol());
    }

    private List<List<String>> distributeCards(List<String> deck) {
        return IntStream.range(0, 4)
                .mapToObj(player -> deck.subList(player * (deck.size() / 4), (player + 1) * (deck.size() / 4)))
                .collect(Collectors.toList());
    }


}
