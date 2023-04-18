package com.practice.test.market.rest;

import com.practice.test.market.dto.CartDTO;
import com.practice.test.market.dto.UserResponseDTO;
import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.Role;
import com.practice.test.market.entity.User;
import com.practice.test.market.service.PurchaseService;
import com.practice.test.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/market/menu")
@CrossOrigin
public class MenuController {
    private UserService userService;

    private PurchaseService purchaseService;

    public MenuController(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/my-profile/{userId}")
    public User authorizeUser(@PathVariable("userId") long userId){

        User newUser = userService.findById(userId);

        return newUser;
    }

    @PutMapping("/balance")
    public ResponseEntity<String> updateBalance(@RequestParam("balance") double newSum, @RequestParam("userid") long id){
        System.out.println("balance update");
        userService.updateBalanceByUser(id, newSum);
        return new ResponseEntity<>("Result:", HttpStatus.OK);
    }

    //test
    @GetMapping("/order-history")
    public ResponseEntity<List<CartDTO>> viewHistory(@RequestParam("userid") long userId){
        //userService.updateBalanceByUser(id, newSum);
        System.out.println("history");
        List<CartDTO> cartDTOS = purchaseService.getUserHistory(userId);
        return new ResponseEntity<>(cartDTOS, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getUserById(@RequestParam("userid") long userId){
        //userService.updateBalanceByUser(id, newSum);

        var user = userService.findById(userId);
        System.out.println("input id: " + userId + "user" + user);
        List<String> roles = new ArrayList<>();
        for(Role role:user.getRoles()){
            roles.add(role.getType());
        }
        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getNickname(),
                user.getEmail(), user.getBalance(),roles);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
