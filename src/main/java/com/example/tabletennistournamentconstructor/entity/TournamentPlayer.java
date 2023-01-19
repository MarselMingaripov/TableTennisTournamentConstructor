package com.example.tabletennistournamentconstructor.entity;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class TournamentPlayer {
    private List<Player> playerList = new ArrayList<>();

    public void addPlayer(Player player){
        playerList.add(player);
    }

    public List<Player> getPlayerList() {
        return playerList;
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
}
