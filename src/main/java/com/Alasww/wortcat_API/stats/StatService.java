package com.Alasww.wortcat_API.stats;

import com.Alasww.wortcat_API.user.User;
import com.Alasww.wortcat_API.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class StatService {
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        return auth.getName();
    }
    @Autowired
    private StatRepo repo;
    @Autowired
    private UserRepo userRepo;
    public Stat getStats(){
        User currentUser = userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        return currentUser.getStat();
    }
    public void checkCardAsLearnt(){
        User currentUser = userRepo.findByEmail(getCurrentUsername()).orElseThrow();
        Stat currentStat=currentUser.getStat();
        currentStat.setLeanrtWordsToday(currentStat.getLeanrtWordsToday()+1);
        repo.save(currentStat);
    }
}
