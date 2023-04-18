package com.practice.test.market;

import com.practice.test.market.dao.ItemDAO;
import com.practice.test.market.dao.OrderDAO;
import com.practice.test.market.dao.UserDAO;
import com.practice.test.market.entity.Item;
import com.practice.test.market.entity.Order;
import com.practice.test.market.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MarketApplication {

	public static void main(String[] args) {

		//SpringApplication.run(MarketApplication.class, args);
		SpringApplication.run(MarketApplication.class, args);
	}


}
