package com.vici.loginprovide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Eureka客户端
 */
@RestController
@EnableEurekaClient
@SpringBootApplication
public class LoginProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginProvideApplication.class, args);
	}


	/**
	 * 假如这个客户端要提供一个getUser的方法
	 * @return
	 */
	@RequestMapping(value = "/getUser")
	public Map<String,Object> getUser(@RequestParam Integer id){
		Map<String,Object> data = new HashMap<>();
		data.put("id",id);
		data.put("userName","admin");
		data.put("from","provider-A");
		return data;
	}
}
