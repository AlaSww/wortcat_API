package com.Alasww.wortcat_API.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CardRepo extends JpaRepository<Card,Long> {

}
