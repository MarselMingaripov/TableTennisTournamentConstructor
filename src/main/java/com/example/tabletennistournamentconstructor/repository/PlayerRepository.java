package com.example.tabletennistournamentconstructor.repository;

import com.example.tabletennistournamentconstructor.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    Optional<Player> findByName(String name);
    Boolean existsByName(String name);
    Player findById(Long id);
    Player findUserByName(String name);
}
