package com.Alasww.wortcat_API.stats;

import com.Alasww.wortcat_API.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int leanrtWordsToday=0;
    private int streak=0;
    private int longestStreak=0;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    public Stat(User user){
        this.user=user;
    }
}