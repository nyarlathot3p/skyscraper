package com.example.MS_vuelos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsVuelosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVuelosApplication.class, args);
	}

}
