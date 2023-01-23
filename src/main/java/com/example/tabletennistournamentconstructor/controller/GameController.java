package com.example.tabletennistournamentconstructor.controller;

import com.example.tabletennistournamentconstructor.entity.Game;
import com.example.tabletennistournamentconstructor.entity.Player;
import com.example.tabletennistournamentconstructor.entity.TournamentService;
import com.example.tabletennistournamentconstructor.repository.GameRepository;
import com.example.tabletennistournamentconstructor.repository.PlayerRepository;
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

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/generateTable")
    public String generateTable(Model model) {
        model.addAttribute("players", tournamentService.getPlayerList());

        /*if (bindingResult.hasErrors()) {
            return "player/create";
        } else {
            if (!playerRepository.findByName(player.getName()).isPresent()) {
                playerRepository.save(player);
                tournamentService.addPlayer(player);
                return "redirect:player/add";
            } else {
                if (tournamentService.getPlayerList().isEmpty()) {
                    tournamentService.addPlayer(player);
                    return "redirect:player/add";
                } else {
                    if (tournamentService.findByName(player.getName()) == null) {
                        tournamentService.addPlayer(player);
                        return "redirect:player/add";
                    }
                }
            }
        }*/

        for (int i = 0; i < tournamentService.getPlayerList().size() - 1; i++) {
            for (int j = i + 1; j < tournamentService.getPlayerList().size(); j++) {
                Game game = tournamentService.addGame(tournamentService.getPlayerList().get(i).getName(),
                        tournamentService.getPlayerList().get(j).getName());
                gameRepository.save(game);

                tournamentService.getPlayerList().get(i).getGameSet().add(game);
                tournamentService.getPlayerList().get(j).getGameSet().add(game);
            }
        }

        for (Player player : tournamentService.getPlayerList()) {
            if (!playerRepository.findByName(player.getName()).isPresent()){
                for (Game game : tournamentService.getGamesMap().values()) {
                    if (game.getFirstPlayer().equals(player.getName()) || game.getSecondPlayer().equals(player.getName())){
                        player.getGameSet().add(game);
                        playerRepository.save(player);
                    }
                }
                //playerRepository.save(player);

            }
        }
        //tournamentService.addGameToPlayer();
        return "table/main";
    }
}
