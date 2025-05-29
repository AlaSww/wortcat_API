package com.Alasww.wortcat_API.deck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepo extends JpaRepository<Deck,Long> {

}
