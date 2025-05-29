package com.Alasww.wortcat_API.card;

import com.Alasww.wortcat_API.deck.Deck;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String front;

    private String back;

    private LocalDateTime lastReviewed=LocalDateTime.now();

    private LocalDateTime nextReview;

    private Duration interval=Duration.ZERO;

    private float easeFactor= 2.5F;

    private int repititions=0;

    private boolean freezed=false;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    public  Card(String front,String back,Deck deck){
        this.front=front;
        this.back=back;
        this.deck=deck;
    }
}
