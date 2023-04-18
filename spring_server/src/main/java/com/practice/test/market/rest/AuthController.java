package com.practice.test.market.rest;

import com.practice.test.market.dto.AuthResponseDTO;
import com.practice.test.market.dto.LoginDto;
import com.practice.test.market.dto.RegisterDTO;
import com.practice.test.market.dto.UserResponseDTO;
import com.practice.test.market.entity.Role;
import com.practice.test.market.entity.User;
import com.practice.test.market.repository.RoleRepository;
import com.practice.test.market.repository.UserRepository;
import com.practice.test.market.security.JWTGenerator;
import com.practice.test.market.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    private UserServiceImpl userService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                          JWTGenerator jwtGenerator, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }




    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
        try {
            System.out.println("nick: " + loginDto.getUsername() + "pass" + loginDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            AuthResponseDTO authResponseDTO= new AuthResponseDTO(token);
            User user = userRepository.findByNickname(loginDto.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
            List<String> roles = new ArrayList<String>();
            for(Role role: user.getRoles()){
                roles.add(role.getType());
            }
            authResponseDTO.setUser(new UserResponseDTO(user.getId(), user.getNickname(), user.getEmail(), user.getBalance(), roles));
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new AuthResponseDTO("no token"), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO registerDto) {
        if (!userRepository.existsByNickname(registerDto.getNickname()) &&
                !userRepository.existsByEmail(registerDto.getEmail())) {
            try {
                User user= new User();
                user.setEmail(registerDto.getEmail());
                user.setNickname(registerDto.getNickname());
                System.out.println("user creation!");
                var password = registerDto.getPassword();
                user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
                if (roleRepository.findByName("client").isPresent()) {
                    Role role = roleRepository.findByName("client").get();
                    Set<Role> roles = new HashSet<>();
                    roles.add(role);
                    user.setRoles(roles);
                    var id = user.getId();
                    user.setBalance(0);
                    System.out.println(registerDto);
                    userService.saveUser(user);

                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    registerDto.getNickname(), password));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String token = jwtGenerator.generateToken(authentication);
                    AuthResponseDTO authResponseDTO = new AuthResponseDTO(token);
                    user = userRepository.findByNickname(registerDto.getNickname())
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
                    List<String> newRoles = new ArrayList<String>();
                    for (Role newRole : user.getRoles()) {
                        newRoles.add(newRole.getType());
                    }
                    authResponseDTO.setUser(new UserResponseDTO(user.getId(), user.getNickname(), user.getEmail(), user.getBalance(), newRoles));
                    return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
                }
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
