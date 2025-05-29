package com.Alasww.wortcat_API.deck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/decks")
public class DeckController {
    @Autowired
    private DeckService deckService;

    @GetMapping
    public List<Deck> getDecks() {
        return deckService.getDecks();
    }

    @PostMapping
    public ResponseEntity<Deck> createDeck(@RequestBody Map<String, String> body) {
        Deck savedDeck = deckService.createDeck(body.get("title"), body.get("description"));
        return ResponseEntity.ok(savedDeck);
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<Deck> getDetails(@PathVariable long deckId) {
        Deck deck = deckService.getDetails(deckId);
        return ResponseEntity.ok(deck);
    }

    @PutMapping("/{deckId}")
    public ResponseEntity<Deck> updateDeck(@PathVariable long deckId, @RequestBody Map<String, String> body) {
        Deck updatedDeck = deckService.updateDeck(deckId, body.get("title"), body.get("description"));
        return ResponseEntity.ok(updatedDeck);
    }

    @DeleteMapping("/{deckId}")
    public void deleteDeck(@PathVariable long deckId) {
        deckService.deleteDeck(deckId);
    }
}
