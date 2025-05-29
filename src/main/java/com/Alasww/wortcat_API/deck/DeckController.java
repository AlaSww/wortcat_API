package com.Alasww.wortcat_API.deck;

import com.Alasww.wortcat_API.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/decks")
public class DeckController {
    @Autowired
    private DeckService deckService;

    @PostMapping("{userId}")
    public ResponseEntity<Deck> create_deck(@PathVariable long userId,@RequestBody Map<String,String> body){
        Deck savedDeck = deckService.create_deck(body.get("title"), body.get("description"), userId);
        return ResponseEntity.ok(savedDeck);
    }
    @GetMapping("/{deck_id}")
    public ResponseEntity<Deck> get_details(@PathVariable long deck_id){
        Deck deck=deckService.get_details(deck_id);
        return  ResponseEntity.ok(deck);
    }
    @PutMapping("/{deck_id}")
    public  ResponseEntity<Deck> update_deck(@PathVariable long deck_id,@RequestBody Map<String,String> body){
        Deck updatedDeck=deckService.update_deck(deck_id,body.get("title"),body.get("description"));
        return  ResponseEntity.ok(updatedDeck);
    }

    @DeleteMapping("/{deck_id}")
    public void delete_deck(@PathVariable long deck_id){
        deckService.delete_deck(deck_id);
    }
}