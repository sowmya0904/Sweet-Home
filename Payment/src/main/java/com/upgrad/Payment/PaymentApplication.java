package com.upgrad.Payment;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}

//	@Bean
//	@LoadBalanced
//	public RestTemplate getRestTemplate(){
//		return new RestTemplate();
//	}
}
