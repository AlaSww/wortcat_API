package com.Alasww.wortcat_API.deck;

import com.Alasww.wortcat_API.card.Card;
import com.Alasww.wortcat_API.user.User;
import com.Alasww.wortcat_API.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {

    @Autowired
    private DeckRepo deckRepo;

    @Autowired
    private UserRepo userRepo;

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        return auth.getName();
    }

    public List<Deck> getDecks() {
        User currentUser = userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        return currentUser.getDecks();
    }

    public Deck createDeck(String title, String description) {
        User user = userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck = new Deck(title, description, user);
        return deckRepo.save(deck);
    }

    public List<Card> getCards(long deckId) {
        User currentUser = userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck = deckRepo.findById(deckId).orElseThrow();

        if (!deck.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You are not the owner of this deck.");
        }

        return deck.getCards();
    }

    public Deck getDetails(long deckId) {
        User currentUser = userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck = deckRepo.findById(deckId).orElseThrow();

        if (!deck.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You are not the owner of this deck.");
        }

        return deck;
    }

    public void deleteDeck(long deckId) {
        User currentUser = userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck = deckRepo.findById(deckId).orElseThrow();

        if (!deck.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You are not the owner of this deck.");
        }

        deckRepo.deleteById(deckId);
    }

    public Deck updateDeck(long deckId, String title, String description) {
        User currentUser = userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck = deckRepo.findById(deckId).orElseThrow();

        if (!deck.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You are not the owner of this deck.");
        }

        if (!deck.getTitle().equals(title)) {
            deck.setTitle(title);
        }

        if (!deck.getDescription().equals(description)) {
            deck.setDescription(description);
        }

        return deckRepo.save(deck);
    }
}