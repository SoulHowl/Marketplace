package com.practice.test.market.rest;

import com.practice.test.market.dto.DefaultResponseDTO;
import com.practice.test.market.dto.ItemResponseDTO;
import com.practice.test.market.dto.ItemResponseListDTO;
import com.practice.test.market.dto.ShopDTO;
import com.practice.test.market.entity.Item;
import com.practice.test.market.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market/shop")
@CrossOrigin(origins = "http://localhost:3000")
public class ShopController {
    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/create")
    public ResponseEntity<ItemResponseDTO> createShopItem(@RequestBody ItemResponseDTO item){
        ItemResponseDTO itemResponseDTO = shopService.createItem(item);
        return new ResponseEntity<>(itemResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ItemResponseDTO> updateShopItem(@RequestBody ItemResponseDTO item){

        ItemResponseDTO res = shopService.updateItem(item);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/delete/")
    public ResponseEntity<DefaultResponseDTO> deleteShopItem(@RequestBody ItemResponseDTO item){
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        defaultResponseDTO.setSuccess(shopService.hideItem(item));
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{userid}/items")
    public ResponseEntity<ShopDTO> getShopItems(@PathVariable long userid){
        ShopDTO list =shopService.getShopItems(userid);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/order/{orderid}")
    public void completeOrder(@PathVariable long id){

    }
}
