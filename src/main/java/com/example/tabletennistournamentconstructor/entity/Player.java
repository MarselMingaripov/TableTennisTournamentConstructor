package com.example.tabletennistournamentconstructor.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "t_player", uniqueConstraints = {
@UniqueConstraint(columnNames = "name")
})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @NotNull
    private String name;
    private int rank;
    private int score;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_games",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> gameSet = new HashSet<>();

    public Player(String name, int rank, int score) {
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    public Player(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Игрок " + " с именем " + name + " и рейтингом " + rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return rank == player.rank && name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rank);
    }
}
