package com.example.tabletennistournamentconstructor.controller;

import com.example.tabletennistournamentconstructor.entity.Player;
import com.example.tabletennistournamentconstructor.entity.TournamentPlayer;
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

    TournamentPlayer tournamentPlayer = new TournamentPlayer();

    @GetMapping("/add")
    public String createPlayer(@ModelAttribute("player") Player player) {
        return "player/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("player") @Valid Player player, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "player/create";
        } else {
            if (!playerRepository.findByName(player.getName()).isPresent()) {
                playerRepository.save(player);
                tournamentPlayer.addPlayer(player);
                return "redirect:player/add";
            } else {
                if (tournamentPlayer.getPlayerList().isEmpty()) {
                    tournamentPlayer.addPlayer(player);
                    return "redirect:player/add";
                } else {
                    if (tournamentPlayer.findByName(player.getName()) == null) {
                        tournamentPlayer.addPlayer(player);
                        return "redirect:player/add";
                    }
                }
            }
        }
        return "redirect:player/add";
    }

    @GetMapping("/show-players")
    public String showPlayers(Model model) {
        model.addAttribute("players", tournamentPlayer.getPlayerList());
        return "player/showAllPlayers";
    }

    @GetMapping("/{name}")
    public String show(@PathVariable("name") String name, Model model) {
        model.addAttribute("player", tournamentPlayer.findByName(name));
        return "player/show";
    }
}
