package com.example.tabletennistournamentconstructor.controller;

import com.example.tabletennistournamentconstructor.config.jwt.JwtUtils;
import com.example.tabletennistournamentconstructor.entity.Erole;
import com.example.tabletennistournamentconstructor.entity.Role;
import com.example.tabletennistournamentconstructor.entity.User;
import com.example.tabletennistournamentconstructor.pojo.JwtResponse;
import com.example.tabletennistournamentconstructor.pojo.LoginRequest;
import com.example.tabletennistournamentconstructor.pojo.MessageResponse;
import com.example.tabletennistournamentconstructor.pojo.SignupRequest;
import com.example.tabletennistournamentconstructor.repository.RoleRepository;
import com.example.tabletennistournamentconstructor.repository.UserRepository;
import com.example.tabletennistournamentconstructor.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;

    /*@PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }*/

    /*@PostMapping("/signin")
    public String authUser(@ModelAttribute("user") LoginRequest loginRequest){
        return "auth/signin";
    }*/
    /*@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        User user = new User(signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail());

        Set<String> reqRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(Erole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(Erole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = roleRepository
                                .findByName(Erole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }*/

    @GetMapping("/signup")
    public String registerUser(@ModelAttribute("registerUser") SignupRequest signupRequest){
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String registration(@ModelAttribute("registerUser") @Valid SignupRequest signupRequest, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "auth/signup";
        }
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            model.addAttribute("Error: Username is exist");
            return "auth/signup";
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            model.addAttribute("Error: Email is exist");
            return "auth/signup";
        }

        User user = new User(signupRequest.getUsername(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail());

        Set<String> reqRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(Erole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(Erole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = roleRepository
                                .findByName(Erole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        if (userRepository.save(user) == null) {
            model.addAttribute("ERRRRRRR");
            return "auth/signup";
        }
        return "redirect:/";
    }
}
