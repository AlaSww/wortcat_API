package com.Alasww.wortcat_API.user;

import com.Alasww.wortcat_API.deck.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;
}
