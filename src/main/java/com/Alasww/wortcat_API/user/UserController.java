package com.Alasww.wortcat_API.user;

import com.Alasww.wortcat_API.deck.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> Create_user(@RequestBody Map<String, String> body){
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");
        User user=new User(name,email,password);
        User savedUser = userService.create_user(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/decks/{user_id}")
    public List<Deck> get_decks(@PathVariable long user_id){
        return userService.get_decks(user_id);
    }
}
