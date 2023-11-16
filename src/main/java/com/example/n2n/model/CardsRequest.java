package com.example.n2n.model;

import com.example.n2n.entity.Cards;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class CardsRequest {
    private List<Cards> cards;
}
