package com.example.n2n.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="cards")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cards implements Comparable<Cards> {

    @Id
    private Integer id;
    private String card;
    private String suit;

    @Override
    public int compareTo(Cards o) {
        return this.id - o.id;
    }
}
