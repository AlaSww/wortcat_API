package com.Alasww.wortcat_API.card;

import com.Alasww.wortcat_API.deck.Deck;
import com.Alasww.wortcat_API.deck.DeckRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private DeckRepo deckRepo;

    @Autowired
    private CardRepo cardRepo;

    public Card createCard(String front, String back, long deckId){
        Deck deck = deckRepo.findById(deckId).orElseThrow();
        Card card = new Card(front, back, deck);
        return cardRepo.save(card);
    }

    public Card moveCard(long cardId, long deckId, long newDeckId) {
        Card card = cardRepo.findById(cardId).orElseThrow();
        Deck deck = deckRepo.findById(deckId).orElseThrow();
        if(card.getDeck().getId() == deckId && deckRepo.existsById(newDeckId)) {
            deck.removeCard(card);
            card.setDeck(deckRepo.findById(newDeckId).orElseThrow());
            return cardRepo.save(card);
        }
        return null;
    }

    public Card copyCard(long cardId, long newDeckId) {
        Deck newDeck = deckRepo.findById(newDeckId).orElseThrow();
        Card oldCard = cardRepo.findById(cardId).orElseThrow();
        Card newCard = new Card(
                oldCard.getFront(),
                oldCard.getBack(),
                newDeck
        );
        return cardRepo.save(newCard);
    }

    public Card updateCard(long cardId, String front, String back) {
        Card card = cardRepo.findById(cardId).orElseThrow();
        card.setFront(front);
        card.setBack(back);
        return cardRepo.save(card);
    }

    // This is also responsible for unfreezing cards
    public Card freezeCard(long cardId) {
        Card card = cardRepo.findById(cardId).orElseThrow();
        card.setFreezed(!card.isFreezed());
        return cardRepo.save(card);
    }

    public Card getDetails(long cardId) {
        return cardRepo.findById(cardId).orElseThrow();
    }

    public void deleteCard(long cardId) {
        cardRepo.deleteById(cardId);
    }

    public List<Card> searchCards(long deckId, String query) {
        Deck deck = deckRepo.findById(deckId).orElseThrow();
        List<Card> allCards = deck.getCards();
        List<Card> filteredCards = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getBack().contains(query) || card.getFront().contains(query)) {
                filteredCards.add(card);
            }
        }
        return filteredCards;
    }

    public Card reviewCard(long cardId, int quality) {
        Card card = cardRepo.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        quality = Math.max(0, Math.min(3, quality));
        LocalDateTime now = LocalDateTime.now();

        if (quality < 2) {
            card.setRepititions(0);
            card.setInterval(Duration.ofMinutes(10));
        } else {
            card.setRepititions(card.getRepititions() + 1);
            if (card.getRepititions() == 1) {
                card.setInterval(Duration.ofMinutes(10));
            } else if (card.getRepititions() == 2) {
                card.setInterval(Duration.ofHours(12));
            } else {
                long intervalMinutes = (long) Math.round(
                        card.getInterval().toMinutes() * card.getEaseFactor()
                );
                card.setInterval(Duration.ofMinutes(intervalMinutes));
            }
        }

        float easeFactor = (float) (
                card.getEaseFactor() + (0.1 - (3 - quality) * (0.08 + (3 - quality) * 0.02))
        );
        card.setEaseFactor(Math.max(easeFactor, 1.3F));

        card.setLastReviewed(now);
        card.setNextReview(now.plus(card.getInterval()));

        return cardRepo.save(card);
    }

    public List<Card> getCardsToReview(long deckId) {
        Deck deck = deckRepo.findById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));

        List<Card> allCards = deck.getCards();
        List<Card> cardsToReview = new ArrayList<>();

        for (Card card : allCards) {
            if (card.getNextReview().isBefore(LocalDateTime.now()) && !card.isFreezed()) {
                cardsToReview.add(card);
            }
        }
        return cardsToReview;
    }
}
