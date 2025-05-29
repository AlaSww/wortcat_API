package com.Alasww.wortcat_API.card;

import com.Alasww.wortcat_API.deck.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/decks/{deck_id}/cards")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    DeckService deckService;

    @GetMapping("/{card_id}")
    public ResponseEntity<Card> get_details(@PathVariable long card_id){
        Card card=cardService.get_details(card_id);
        return ResponseEntity.ok(card);
    }

    @DeleteMapping("/{card_id}")
    public void delete_card(@PathVariable long card_id){
        cardService.delete_card(card_id);
    }

    @PostMapping
    public ResponseEntity<Card> create_card(
            @PathVariable("deck_id") long deck_id,
            @RequestBody Map<String,String> body
    ){
        Card savedCard= cardService.create_card(body.get("front"),body.get("back"),deck_id);
        return ResponseEntity.ok(savedCard);
    }

    @PutMapping("{card_id}")
    public ResponseEntity<Card> edit_back(
            @PathVariable("deck_id") long deck_id,
            @PathVariable("card_id") long card_id, 
            @RequestBody Map<String,String> body
    ){
        Card updatedCard= cardService.update_card(card_id,body.get("front"),body.get("back"));
        return  ResponseEntity.ok(updatedCard);
    }
    @GetMapping
    public List<Card> get_cards(@PathVariable("deck_id") long deck_id){
        return deckService.get_cards(deck_id);
    }
    @PutMapping("/{card_id}/move/{new_deck_id}")
    public ResponseEntity<Card> move_card(
            @PathVariable("deck_id") long deck_id,
            @PathVariable("card_id") long card_id,
            @PathVariable("new_deck_id") long new_deck_id
    ){
        Card movedCard= cardService.move_card(card_id,deck_id,new_deck_id);
        return ResponseEntity.ok(movedCard);
    }

    @PostMapping("/{card_id}/copy/{new_deck_id}")
    public ResponseEntity<Card> copy_card(
            @PathVariable("deck_id") long deck_id,
            @PathVariable("card_id") long card_id,
            @PathVariable("new_deck_id") long new_deck_id
    ){
        Card copiedCard= cardService.copy_card(card_id,new_deck_id);
        return  ResponseEntity.ok(copiedCard);
    }

    //this is also responsable of unfreezing cards
    @PutMapping("/{card_id}/freeze")
    public ResponseEntity<Card> freeze_card(@PathVariable long card_id){
        Card freezedCard=cardService.freeze_card(card_id);
        return ResponseEntity.ok(freezedCard);
    }

    @GetMapping("search/{query}")
    public List<Card> search_cards(@PathVariable("deck_id") long deck_id,
                                   @PathVariable("query") String query
    ){
        return cardService.search_cards(deck_id,query);
    }
}
