package com.example.tabletennistournamentconstructor.controller;

import com.example.tabletennistournamentconstructor.entity.Player;
import com.example.tabletennistournamentconstructor.entity.TournamentService;
import com.example.tabletennistournamentconstructor.repository.GameRepository;
import com.example.tabletennistournamentconstructor.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TournamentService tournamentService;

    @Autowired
    GameRepository gameRepository;

    @GetMapping("/add")
    public String createPlayer(@ModelAttribute("player") Player player) {
        return "player/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("player") @Valid Player player, BindingResult bindingResult) {
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

        if (bindingResult.hasErrors()) {
            return "player/create";
        } else {
            tournamentService.addPlayer(player);
        }
        return "redirect:player/add";
    }

    @GetMapping("/show-players")
    public String showPlayers(Model model) {
        model.addAttribute("players", tournamentService.getPlayerList());
        return "player/showAllPlayers";
    }

    @GetMapping("/{name}")
    public String show(@PathVariable("name") String name, Model model) {
        model.addAttribute("player", tournamentService.findByName(name));
        return "player/show";
    }
}
