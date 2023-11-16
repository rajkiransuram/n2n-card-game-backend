package com.example.n2n.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardPlayer {

    private String name;
    private List<Card> cards = new ArrayList<>();

    public CardPlayer(Card... cards) {
        Collections.addAll(this.cards, cards);
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
