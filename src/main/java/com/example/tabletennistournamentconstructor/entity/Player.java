package com.example.tabletennistournamentconstructor.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_player", uniqueConstraints = {
@UniqueConstraint(columnNames = "name")
})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int rank;
    private int score;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_game",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> gameSet = new HashSet<>();

    public Player(String name, int rank, int score) {
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    public Player(String name, int rank, int score, Set<Game> gameSet) {
        this.name = name;
        this.rank = rank;
        this.score = score;
        this.gameSet = gameSet;
    }

    public Player() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Set<Game> getGameSet() {
        return gameSet;
    }

    public void setGameSet(Set<Game> gameSet) {
        this.gameSet = gameSet;
    }
}
