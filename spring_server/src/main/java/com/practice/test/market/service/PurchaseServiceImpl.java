package com.practice.test.market.service;

import com.practice.test.market.dao.PurchaseDAO;
import com.practice.test.market.dto.CartDTO;
import com.practice.test.market.dto.CartItemDTO;
import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.OrderedItem;
import com.practice.test.market.entity.OrderedItemStatus;
import com.practice.test.market.entity.User;
import com.practice.test.market.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    private PurchaseDAO purchaseDAO;

    private OrderRepository orderRepository;
    private OrderedItemRepository orderedItemRepository;

    private UserRepository userRepository;

    private StatusRepository statusRepository;
    private OrderedItemStatusRepository orderedItemStatusRepository;

    private ItemRepository itemRepository;

    public PurchaseServiceImpl(PurchaseDAO purchaseDAO, OrderRepository orderRepository,
                               OrderedItemRepository orderedItemRepository, UserRepository userRepository,
                               StatusRepository statusRepository,
                               OrderedItemStatusRepository orderedItemStatusRepository,
                               ItemRepository itemRepository) {
        this.purchaseDAO = purchaseDAO;
        this.orderRepository = orderRepository;
        this.orderedItemRepository = orderedItemRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.orderedItemStatusRepository = orderedItemStatusRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public CartDTO getCart(long id) {
        Order order = orderRepository.findOrderByUser_IdAndStatus_TypeEquals(id, "inventory");
        CartDTO cartDTO = new CartDTO();
        List<CartItemDTO> list = new ArrayList<CartItemDTO>();
        assert order.getOrderedItems() != null;
        double sum = 0;
        for(OrderedItem odIt:order.getOrderedItems()){
            list.add(new CartItemDTO(odIt.getId(), odIt.getItem().getShop().getName(), odIt.getItem().getTitle(),odIt.getItem().getDescription(),
                    odIt.getQuantity(), odIt.getPrice(), odIt.getOrderedItemStatus().getType(), odIt.getValue()));
            sum += odIt.getQuantity() * odIt.getPrice();
        }
        cartDTO.setItems(list);
        cartDTO.setId(order.getId());
        cartDTO.setDatePurchase(null);
        cartDTO.setSum(sum);
        return cartDTO;
    }

    @Override
    @Transactional
    public void deleteCartItem(long id) {
       orderedItemRepository.deleteById((int)id);
       orderedItemRepository.flush();
    }
    @Override
    public boolean checkDeleted(long id) {
        return orderedItemRepository.existsById((int) id);
    }
    @Override
    @Transactional
    public long updateCartItemQuantity(CartItemDTO orderedItem) {
        OrderedItem odIt = orderedItemRepository.findOrderedItemById(orderedItem.getId());
        odIt.setQuantity(orderedItem.getQuantity());
        orderedItemRepository.save(odIt);
        OrderedItem odIt2 = orderedItemRepository.findOrderedItemById(orderedItem.getId());
        return odIt2.getQuantity();
    }

    @Override
    @Transactional
    public boolean makeOrder(long id) {
        Order order = orderRepository.findOrderByUser_IdAndStatus_TypeEquals(id, "inventory");
        User user = order.getUser();
        assert order.getOrderedItems() != null;
        for(OrderedItem odIt: order.getOrderedItems()){
            if(odIt.getItem().getQuantity() - odIt.getQuantity() < 0){
                return false;
            }
        }
        if(user.getBalance() - order.getSum()> 0) {

            userRepository.save(user);
            order.setPurchaseDate(new Date());
            order.setStatus(statusRepository.findStatusByType("purchased"));
            assert order.getOrderedItems() != null;
            double sum = 0;
            for(OrderedItem odIt: order.getOrderedItems()){
                sum += odIt.getQuantity() * odIt.getPrice();
                odIt.setOrderedItemStatus(orderedItemStatusRepository.findByType("active"));
                var it = odIt.getItem();
                it.setQuantity(it.getQuantity() - odIt.getQuantity());
                itemRepository.save(it);
            }
            user.setBalance(user.getBalance() - sum);
            orderedItemRepository.saveAll(order.getOrderedItems());
            orderRepository.save(order);
            Order newOrder = new Order();
            newOrder.setUser(user);
            newOrder.setStatus(statusRepository.findStatusByType("inventory"));
            newOrder.setSum(0.0);
            orderRepository.save(newOrder);
            return true;
        }
        return false;
    }

    @Override
    public List<CartDTO> getUserHistory(long id) {

        List<Order> orders =orderRepository.findOrdersByUser_IdAndStatus_Type(id, "purchased");
        List<CartDTO> cartDTOS = new ArrayList<>();
        for(Order order: orders){
            CartDTO cartDTO = new CartDTO();
            double sum = 0;
            assert order.getOrderedItems() != null;
            List<CartItemDTO> list = new ArrayList<>();
            for(OrderedItem odIt:order.getOrderedItems()){
                list.add(new CartItemDTO(odIt.getId(), odIt.getItem().getShop().getName(), odIt.getItem().getTitle(),odIt.getItem().getDescription(),
                        odIt.getQuantity(), odIt.getPrice(), odIt.getOrderedItemStatus().getType(), odIt.getValue()));
                sum += odIt.getQuantity() * odIt.getPrice();
            }
            cartDTO.setItems(list);
            cartDTO.setId(order.getId());
            cartDTO.setDatePurchase(null);
            cartDTO.setSum(sum);
            cartDTOS.add(cartDTO);
        }
        return cartDTOS;

    }
}
