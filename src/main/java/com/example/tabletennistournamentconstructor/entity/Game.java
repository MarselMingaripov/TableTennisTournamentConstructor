package com.example.tabletennistournamentconstructor.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstPlayer;
    private String secondPlayer;
    private int firstPlayerScore;
    private int secondPlayerScore;
    private int gameResult;

    public Game() {
    }

    public Game(String firstPlayer, String secondPlayer, int firstPlayerScore, int secondPlayerScore) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public void setFirstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public void setSecondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    public int getGameResult() {
        return gameResult;
    }

    public void setGameResult(int firstPlayerScore, int secondPlayerScore) {
        if (firstPlayerScore > secondPlayerScore) {
            if (firstPlayerScore > secondPlayerScore + 1) {
                this.gameResult = 2;
            } else {
                this.gameResult = 1;
            }
        } else {
            this.gameResult = 0;
        }
    }
}
