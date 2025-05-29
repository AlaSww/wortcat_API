package com.Alasww.wortcat_API.deck;

import com.Alasww.wortcat_API.card.Card;
import com.Alasww.wortcat_API.user.User;
import com.Alasww.wortcat_API.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {

    @Autowired
    private DeckRepo deckRepo;
    @Autowired
    private UserRepo userRepo;

    public Deck create_deck(String title, String description, long userId){
        Deck deck = new Deck(title, description, userRepo.findById(userId).orElseThrow());
        return deckRepo.save(deck);
    }

    public List<Card> get_cards(long deck_id){
        Deck deck= deckRepo.findById(deck_id).orElseThrow();
        return deck.getCards();
    }

    public Deck get_details(long deck_id) {
        return deckRepo.findById(deck_id).orElseThrow();
    }
    public  void delete_deck(long deck_id){
        deckRepo.deleteById(deck_id);
    }

    public Deck update_deck(long deck_id, String title, String description) {
        Deck deck=deckRepo.findById(deck_id).orElseThrow();
        if(deck.getTitle()!=title){deck.setTitle(title);}
        if(deck.getDescription()!=description){deck.setDescription(description);}
        return deckRepo.save(deck);
    }
}
