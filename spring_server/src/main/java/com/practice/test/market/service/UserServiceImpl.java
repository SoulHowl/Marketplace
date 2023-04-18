package com.practice.test.market.service;

import com.practice.test.market.dao.PurchaseDAO;
import com.practice.test.market.dao.UserDAO;
import com.practice.test.market.dto.RatingResponseDTO;
import com.practice.test.market.entity.Shop;
import com.practice.test.market.entity.User;
import com.practice.test.market.entity.UserScore;
import com.practice.test.market.repository.RatingRepository;
import com.practice.test.market.repository.RoleRepository;
import com.practice.test.market.repository.ShopRepository;
import com.practice.test.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{
    private UserDAO userDAO;
    private UserRepository userRepository;
    private RatingRepository ratingRepository;
    private PurchaseDAO purchaseDAO;
    private ShopRepository shopRepository;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserRepository userRepository, RatingRepository ratingRepository, PurchaseDAO purchaseDAO, ShopRepository shopRepository) {
        this.userDAO = userDAO;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.purchaseDAO = purchaseDAO;
        this.shopRepository = shopRepository;
    }

    @Override
    public void saveUser(User user) {
        var us = userDAO.saveUser(user);
        purchaseDAO.createCart(us);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User authorizeByEmailPass(String email, String pass) {
        return userDAO.authorizeByEmailPass(email, pass);
    }

    @Override
    public void updateBalanceByUser(long id, double newBalance) {
        userDAO.topUpBalance(id, newBalance);
    }

    @Override
    @Transactional
    public RatingResponseDTO setScore(long id, int rating, long shopId) {
        double shopRating = 0;
        Shop shop = shopRepository.findById(shopId);
        UserScore existingScore = ratingRepository.getUserScoreByUser_Id(id);
        if(existingScore != null){
            existingScore.setRateScore(rating);
            ratingRepository.save(existingScore);
        }
        else{
            User user = userRepository.findById(id);
            UserScore us = new UserScore(shop, user, rating);
            ratingRepository.save(us);

        }
        shop = shopRepository.findById(shopId);
        int count = 0;
        for(UserScore userScore: shop.getRates()){
            shopRating += userScore.getRateScore();
            count++;
        }
        return new RatingResponseDTO(String.valueOf((shopRating + rating) / (count + 1)), String.valueOf(rating));
    }

//    @Override
//    public void updateScore(long id, int rating, long shopId) {
//        UserScore us = ratingRepository.getUserScoreByUser_Id(id);
//        User user = userRepository.findById(id);
//        Shop shop = shopRepository.findById(shopId);
//        us.setRateScore(rating);
//        ratingRepository.save(us);
//    }


}
