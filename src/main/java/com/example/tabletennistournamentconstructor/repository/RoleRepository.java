package com.example.tabletennistournamentconstructor.repository;

import com.example.tabletennistournamentconstructor.entity.Erole;
import com.example.tabletennistournamentconstructor.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Erole roleName);
}
