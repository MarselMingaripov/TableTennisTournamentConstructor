package com.example.tabletennistournamentconstructor.controller;

import com.example.tabletennistournamentconstructor.entity.Erole;
import com.example.tabletennistournamentconstructor.entity.Role;
import com.example.tabletennistournamentconstructor.entity.User;
import com.example.tabletennistournamentconstructor.repository.RoleRepository;
import com.example.tabletennistournamentconstructor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(path = "/get-all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> find(@PathVariable("id") long id) {
        boolean isAdmin = false;
        User user = userRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(auth.getName());
        Set<Role> roles = currentUser.getRoles();

        for (Role role : roles) {
            if (role.getName().toString() == "ROLE_ADMIN")
                isAdmin = true;
        }
        if (user.getUsername().equals(auth.getName()) || isAdmin) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PostMapping(path = "/create-user", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public User create(@RequestBody User user) {
        User newUser = new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getEmail());
        Set<Role> reqRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles.isEmpty()) {
            Role userRole = roleRepository
                    .findByName(Erole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);

        } else {
            for (Role role : reqRoles) {
                if (Erole.ROLE_ADMIN.equals(role.getName())) {
                    Role adminRole = roleRepository
                            .findByName(Erole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository
                            .findByName(Erole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                    roles.add(userRole);
                }
            }
        }
        newUser.setRoles(roles);
        userRepository.save(newUser);

        return newUser;
    }


    @DeleteMapping(path = "/delete/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable("username") String username) {
        userRepository.deleteById(username);
    }

    @PutMapping(path = "/update/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public User update(@PathVariable("username") String username, @RequestBody User user) throws Exception {
        if (userRepository.existsById(username)) {
            user.setUsername(username);
            return userRepository.save(user);
        } else {
            throw new Exception("Bad Request!!!");
        }
    }
}
