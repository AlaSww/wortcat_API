package com.Alasww.wortcat_API.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatScheduler {
    @Autowired
    private StatRepo repo;

    @Scheduled(cron= "0 0 0 * * *")
    public void resetStats(){
        List<Stat> stats = repo.findAll();
        for(Stat stat:stats){
            if (stat.getLeanrtWordsToday()==0){
                stat.setStreak(0);
            }
            if (stat.getStreak()> stat.getLongestStreak()){
                stat.setLongestStreak(stat.getStreak());
            }
            stat.setLeanrtWordsToday(0);
        }
        repo.saveAll(stats);
    }
}