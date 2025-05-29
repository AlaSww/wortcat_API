package com.Alasww.wortcat_API.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats")
public class StatController {
    @Autowired
    private StatService statService;
    @GetMapping
    public Stat getStats(){
        return statService.getStats();
    }
}
