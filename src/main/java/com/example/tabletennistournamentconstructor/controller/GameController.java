package com.example.tabletennistournamentconstructor.controller;

import com.example.tabletennistournamentconstructor.entity.Player;
import com.example.tabletennistournamentconstructor.entity.TournamentService;
import com.example.tabletennistournamentconstructor.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    TournamentService tournamentService;

    @Autowired
    GameRepository gameRepository;

    @GetMapping("/generateTable")
    public String generateTable(Model model){
        model.addAttribute("players", tournamentService.getPlayerList());
        for (int i = 0; i < tournamentService.getPlayerList().size() - 1; i++) {
            for (int j = i + 1; j < tournamentService.getPlayerList().size(); j++) {
                gameRepository.save(tournamentService.addGame(tournamentService.getPlayerList().get(i).getName(),
                        tournamentService.getPlayerList().get(j).getName()));
            }

        }
        return "table/main";
    }
}
