package com.Alasww.wortcat_API.deck;

import com.Alasww.wortcat_API.card.Card;
import com.Alasww.wortcat_API.user.User;
import com.Alasww.wortcat_API.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
    public List<Deck> getDecks(){
        User currentUser=userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        return currentUser.getDecks();
    }
    public Deck create_deck(String title, String description){
        Deck deck = new Deck(title, description, userRepo.findByEmail(getCurrentUsername()).orElseThrow());
        return deckRepo.save(deck);
    }

    public List<Card> getCards(long deckId) {
        User currentUser=userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck = deckRepo.findById(deckId)
                .orElseThrow();

        if (!deck.getUser().equals(currentUser)) {
            throw new AccessDeniedException("You are not the owner of this deck.");
        }

        return deck.getCards();
    }


    public Deck get_details(long deckId) {
        User currentUser=userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck = deckRepo.findById(deckId)
                .orElseThrow();
        if(!deck.getUser().equals(currentUser)){
            throw new AccessDeniedException("you are not the owner of this deck");
        }
        return deck;
    }
    public  void delete_deck(long deckId){
        User currentUser=userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck=deckRepo.findById(deckId).orElseThrow();
        if(!deck.getUser().equals(currentUser)){
            throw new AccessDeniedException("you are not the owner of this deck");
        }
        deckRepo.deleteById(deckId);
    }

    public Deck update_deck(long deckId, String title, String description) {
        User currentUser=userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Deck deck=deckRepo.findById(deckId).orElseThrow();
        if(!deck.getUser().equals(currentUser)){
            throw new AccessDeniedException("you are not the owner of this deck");
        }
        if(deck.getTitle()!=title){deck.setTitle(title);}
        if(deck.getDescription()!=description){deck.setDescription(description);}
        return deckRepo.save(deck);
    }
}
