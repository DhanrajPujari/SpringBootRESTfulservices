package com.multigen.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringBootResTfulservicesApplication {

	public static void main(String[] args) {

        SpringApplication.run(SpringBootResTfulservicesApplication.class, args);
        System.out.println("Application Started...");

	}

}
