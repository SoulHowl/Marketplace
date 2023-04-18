package com.practice.test.market.dao;

import com.practice.test.market.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAOImpl implements UserDAO{
    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByNickname(String nickname) {
        return entityManager.find(User.class, nickname);
    }

    @Override
    public User authorizeByEmailPass(String email, String pass) {


        User theQuery = entityManager.createQuery
                ("From User where email= :emailData and password=:passData", User.class).
                setParameter("emailData", email).
                setParameter("passData", pass).
                getSingleResult();

        return theQuery;

    }

    @Override
    @Transactional
    public void topUpBalance(long id, double newBalance) {
        User me= entityManager.find(User.class, id);
        me.setBalance(newBalance);
        entityManager.merge(me);
    }
}
