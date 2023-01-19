package com.example.tabletennistournamentconstructor.controller;

import com.example.tabletennistournamentconstructor.entity.TournamentPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    TournamentPlayer tournamentPlayer;

    @GetMapping("/generateTable")
    public String generateTable(Model model){
        model.addAttribute("players", tournamentPlayer.getPlayerList());
        return "table/main";
    }
}
