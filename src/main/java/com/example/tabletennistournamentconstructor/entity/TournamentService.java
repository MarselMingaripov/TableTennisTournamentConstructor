package com.example.tabletennistournamentconstructor.entity;
import com.example.tabletennistournamentconstructor.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TournamentService {

    @Autowired
    GameRepository gameRepository;

    private List<Player> playerList = new ArrayList<>();

    private Map<String, Game> gamesMap = new HashMap<>();


    public void addPlayer(Player player){
        playerList.add(player);
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Map<String, Game> getGamesMap() {
        return gamesMap;
    }

    public Player findByName(String name){
        Player player = null;
        for (Player players : playerList) {
            if (players.getName().equals(name)){
                player = players;
            }
        }
        return player;
    }

    public boolean isExist(Player player){
        if (playerList.isEmpty() || findByName(player.getName()) == null){
            return true;
        }
        else {
            return false;
        }
    }
    public Game addGame(String player1, String player2){
        Game game = new Game();
        String key = player1 + "-" + player2;
        if (!player1.equals(player2)) {
            game.setFirstPlayer(player1);
            game.setSecondPlayer(player2);
            gamesMap.put(key, game);
            //gameRepository.save(gamesMap.get(key));
        }
        return game;
    }

    public void addGameToPlayer(){
        for (Player player : playerList) {
            for (Game value : gamesMap.values()) {
                if (player.getName().equals(value.getFirstPlayer())){
                    player.getGameSet().add(value);
                }

            }
        }
    }
}
