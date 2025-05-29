package com.Alasww.wortcat_API.deck;

import com.Alasww.wortcat_API.card.Card;
import com.Alasww.wortcat_API.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String title;

    private String description;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "deck" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Deck(String title, String description, User user) {
        this.title=title;
        this.description=description;
        this.user=user;
    }
    public void removeCard(Card card){
        cards.remove(card);
        card.setDeck(null);
    }
}
