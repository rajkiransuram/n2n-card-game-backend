package com.example.n2n.model;

public class Card  implements Comparable<Card>{
    private String symbol;

    public Card() {
    }

    public Card(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public int compareTo(Card other) {
        return this.symbol.compareTo(other.getSymbol());
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


}
