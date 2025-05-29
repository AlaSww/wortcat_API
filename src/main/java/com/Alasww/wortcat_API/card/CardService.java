package com.Alasww.wortcat_API.card;

import com.Alasww.wortcat_API.deck.Deck;
import com.Alasww.wortcat_API.deck.DeckRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private DeckRepo deckRepo;

    @Autowired
    private CardRepo cardRepo;

    public Card create_card(String front, String back, long deck_id){
        Deck deck = deckRepo.findById(deck_id).orElseThrow();
        Card card= new Card(front,back,deck);
        return  cardRepo.save(card);
    }

    public Card move_card(long card_id, long deck_id, long new_deck_id) {
        Card card=cardRepo.findById(card_id).orElseThrow();
        Deck deck = deckRepo.findById(deck_id).orElseThrow();
        if(card.getDeck().getId()==deck_id
           && deckRepo.existsById(new_deck_id)
        ){
            deck.remove_card(card);
            card.setDeck(deckRepo.findById(new_deck_id).orElseThrow());
            return cardRepo.save(card);
        }
        return null;
    }

    public Card copy_card(long card_id, long new_deck_id) {
        Deck new_deck= deckRepo.findById(new_deck_id).orElseThrow();
        Card old_card=cardRepo.findById(card_id).orElseThrow();
        Card new_card=new Card(
                old_card.getFront(),
                old_card.getBack(),
                new_deck
        );
        return cardRepo.save(new_card);
    }

    public Card update_card(long card_id, String front, String back) {
        Card card=cardRepo.findById(card_id).orElseThrow();
        card.setFront(front);
        card.setBack(back);
        return cardRepo.save(card);
    }

    //this is also responsable of unfreezing cards
    public Card freeze_card(long card_id) {
        Card card= cardRepo.findById(card_id).orElseThrow();
        if (card.isFreezed()){
            card.setFreezed(false);
        }
        else {
            card.setFreezed(true);
        }
        return cardRepo.save(card);
    }

    public Card get_details(long card_id) {
        return cardRepo.findById(card_id).orElseThrow();
    }

    public void delete_card(long card_id) {
        cardRepo.deleteById(card_id);
    }

    public List<Card> search_cards(long deck_id,String query) {
        Deck deck=deckRepo.findById(deck_id).orElseThrow();
        List<Card> all_cards=deck.getCards();
        List<Card> filtered_cards=new ArrayList<>();
        for (Card i:all_cards){
            if (i.getBack().contains(query) || i.getFront().contains(query)){
                filtered_cards.add(i);
            }
        }
        return filtered_cards;
    }
}
