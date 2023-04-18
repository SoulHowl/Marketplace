package com.practice.test.market.rest;

import com.practice.test.market.dto.CartDTO;
import com.practice.test.market.dto.CartItemDTO;
import com.practice.test.market.dto.DefaultResponseDTO;
import com.practice.test.market.entity.Order;
import com.practice.test.market.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market/menu/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private PurchaseService purchaseService;
    @Autowired
    public CartController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/content/{userId}")
    public ResponseEntity<CartDTO> viewCart(@PathVariable long userId){
        try {
            System.out.println("Cart");
            var cart = purchaseService.getCart(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/content/update")
    public ResponseEntity<String> updateCartItem(@RequestBody CartItemDTO cartItemDTO){
        System.out.println("update cart item");
        var newQuantity = purchaseService.updateCartItemQuantity(cartItemDTO);
        return new ResponseEntity<>("Updated qua =" + newQuantity, HttpStatus.OK);
    }

    @DeleteMapping("/content/{oditemid}/delete")
    public ResponseEntity<DefaultResponseDTO> deleteCartItem(@PathVariable long oditemid){
        try {
            purchaseService.deleteCartItem(oditemid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        boolean res = purchaseService.checkDeleted(oditemid);
                DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO(res, "");
        return new  ResponseEntity<>(defaultResponseDTO, HttpStatus.OK);
    }

    // must test
    @GetMapping("/make-order")
    public ResponseEntity<DefaultResponseDTO> makeOrder(@RequestParam("userid") long userId){
        System.out.println("making order");
        var res = purchaseService.makeOrder(userId);

        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        defaultResponseDTO.setSuccess(res);
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.OK);
    }
}
