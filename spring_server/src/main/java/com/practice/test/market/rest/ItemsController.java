package com.practice.test.market.rest;

import com.practice.test.market.dto.*;
import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.UserScore;
import com.practice.test.market.service.ItemService;
import com.practice.test.market.service.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/market")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemsController {
    private ItemService itemService;

    private UserService userService;

    @Autowired
    public ItemsController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping("/main")

    public ResponseEntity<ItemResponseListDTO> getFeed(@RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "category", required = false) String category,
                                              @RequestParam(value = "platform", required = false) String platform,
                                              @RequestParam(value="byprice", defaultValue = "false", required = false) boolean byPrice,
                                              @RequestParam(value="bytitle", defaultValue = "false", required = false) boolean byTitle,
                                                       @RequestParam(value="asc", defaultValue = "false", required = false) boolean asc,
                                              @RequestParam(value = "pageNo", defaultValue = "0", required = false)
                                                           int pageNo,
                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false)
                                                           int pageSize){
        System.out.println( " cat: " + category + " platform: " + platform + "title:" + title);

        ItemResponseListDTO items = itemService.findAll(title, category == null? "": category, platform == null? "": platform,
                 PageRequest.of( pageNo, pageSize, asc ? Sort.Direction.ASC: Sort.Direction.DESC, byPrice? "price":"title"), pageNo, pageSize);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/main/{itemId}")
    public ResponseEntity<CurrentItemResponseDTO> getCurrentItem(@PathVariable long itemId, @RequestParam("userid") long userId){
        CurrentItemResponseDTO currentItemResponseDTO = itemService.getItem(itemId, userId);
        return new ResponseEntity<>(currentItemResponseDTO, HttpStatus.OK);
    }

//    @PutMapping("/main/shop_rating/update")
//    public ResponseEntity<String> updateShopRating(@RequestParam("userid") long userId,
//                                                   @RequestParam("myrating") int rate,
//                                                   @RequestParam("shopid") long shopId){
//        userService.updateScore(userId, rate, shopId);
//        return new ResponseEntity<>("Updating rating", HttpStatus.OK);
//    }
    @PostMapping("/main/shop_rating/set")
    public ResponseEntity<RatingResponseDTO> setShopRating(@RequestParam("userid") long userId,
                                                           @RequestParam("myrating") int rate,
                                                           @RequestParam("shopid") long shopId){
        try {
            RatingResponseDTO res = userService.setScore(userId, rate, shopId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/main/{itemId}/add")
    public ResponseEntity<String> addToCart(@PathVariable long itemId, @RequestParam("userid") long userId,
                          @RequestParam("quantity") long quantity){
        try{
        var res = itemService.addItemToCart(itemId, userId, quantity);

            return new ResponseEntity<>("Resulted in success: " + res, HttpStatus.OK);

        }
        catch (NoResultException e){
            return new ResponseEntity<>("Resulted in bad: " + e.getMessage(), HttpStatus.NO_CONTENT);
        }

    }
}
