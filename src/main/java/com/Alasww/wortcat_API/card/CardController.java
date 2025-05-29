package com.Alasww.wortcat_API.card;

import com.Alasww.wortcat_API.deck.DeckService;
import com.Alasww.wortcat_API.stats.Stat;
import com.Alasww.wortcat_API.stats.StatService;
import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/decks/{deckId}/cards")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    DeckService deckService;
    @Autowired
    StatService statService;

    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getDetails(@PathVariable long cardId){
        Card card = cardService.getDetails(cardId);
        return ResponseEntity.ok(card);
    }

    @DeleteMapping("/{cardId}")
    public void deleteCard(@PathVariable long cardId){
        cardService.deleteCard(cardId);
    }

    @PostMapping
    public ResponseEntity<Card> createCard(
            @PathVariable("deckId") long deckId,
            @RequestBody Map<String,String> body
    ){
        Card savedCard = cardService.createCard(body.get("front"), body.get("back"), deckId);
        return ResponseEntity.ok(savedCard);
    }

    @PutMapping("{cardId}")
    public ResponseEntity<Card> editBack(
            @PathVariable("deckId") long deckId,
            @PathVariable("cardId") long cardId,
            @RequestBody Map<String,String> body
    ){
        Card updatedCard = cardService.updateCard(cardId, body.get("front"), body.get("back"));
        return ResponseEntity.ok(updatedCard);
    }

    @GetMapping
    public List<Card> getCards(@PathVariable("deckId") long deckId){
        return deckService.getCards(deckId);
    }

    @PutMapping("/{cardId}/move/{newDeckId}")
    public ResponseEntity<Card> moveCard(
            @PathVariable("deckId") long deckId,
            @PathVariable("cardId") long cardId,
            @PathVariable("newDeckId") long newDeckId
    ){
        Card movedCard = cardService.moveCard(cardId, deckId, newDeckId);
        return ResponseEntity.ok(movedCard);
    }

    @PostMapping("/{cardId}/copy/{newDeckId}")
    public ResponseEntity<Card> copyCard(
            @PathVariable("deckId") long deckId,
            @PathVariable("cardId") long cardId,
            @PathVariable("newDeckId") long newDeckId
    ){
        Card copiedCard = cardService.copyCard(cardId, newDeckId);
        return ResponseEntity.ok(copiedCard);
    }

    // this is also responsible for unfreezing cards
    @PutMapping("/{cardId}/freeze")
    public ResponseEntity<Card> freezeCard(@PathVariable("cardId") long cardId){
        Card freezedCard = cardService.freezeCard(cardId);
        return ResponseEntity.ok(freezedCard);
    }

    @GetMapping("search/{query}")
    public List<Card> searchCards(@PathVariable("deckId") long deckId,
                                  @PathVariable("query") String query
    ){
        return cardService.searchCards(deckId, query);
    }

    @PutMapping("/review/{cardId}")
    public ResponseEntity<Card> reviewCard(
            @PathVariable("cardId") long cardId,
            @RequestBody Map<String,Integer> body
    ){
        Card reviewedCard = cardService.reviewCard(cardId, body.get("quality"));
        statService.checkCardAsLearnt();
        return ResponseEntity.ok(reviewedCard);
    }

    @GetMapping("/due")
    public List<Card> getCardsToReview(@PathVariable("deckId") long deckId){
        return cardService.getCardsToReview(deckId);
    }
}
