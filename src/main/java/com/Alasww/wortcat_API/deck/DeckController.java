package com.Alasww.wortcat_API.deck;

import com.Alasww.wortcat_API.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/decks")
public class DeckController {
    @Autowired
    private DeckService deckService;

    @GetMapping
    public List<Deck> getDecks(){
        return deckService.getDecks();
    }
    @PostMapping
    public ResponseEntity<Deck> create_deck(@RequestBody Map<String,String> body){
        Deck savedDeck = deckService.create_deck(body.get("title"), body.get("description"));
        return ResponseEntity.ok(savedDeck);
    }
    @GetMapping("/{deckId}")
    public ResponseEntity<Deck> get_details(@PathVariable long deckId){
        Deck deck=deckService.get_details(deckId);
        return  ResponseEntity.ok(deck);
    }
    @PutMapping("/{deckId}")
    public  ResponseEntity<Deck> update_deck(@PathVariable long deck_id,@RequestBody Map<String,String> body){
        Deck updatedDeck=deckService.update_deck(deck_id,body.get("title"),body.get("description"));
        return  ResponseEntity.ok(updatedDeck);
    }

    @DeleteMapping("/{deckId}")
    public void delete_deck(@PathVariable long deckId){
        deckService.delete_deck(deckId);
    }
}