package com.Alasww.wortcat_API.user;

import com.Alasww.wortcat_API.deck.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    public User create_user(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

//        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
//        user.setPasswordHash(hashedPassword);

        return repo.save(user);
    }

    public void deleteUser(long id){
        repo.deleteById(id);
    }
    public List<Deck> get_decks(long user_id){
        User user= repo.findById(user_id).orElseThrow();
        return user.getDecks();
    }
}
