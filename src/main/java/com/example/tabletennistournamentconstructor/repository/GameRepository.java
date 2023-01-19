package com.example.tabletennistournamentconstructor.repository;

import com.example.tabletennistournamentconstructor.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
}
