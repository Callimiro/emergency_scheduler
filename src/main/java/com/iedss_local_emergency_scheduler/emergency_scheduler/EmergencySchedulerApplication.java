package com.iedss_local_emergency_scheduler.emergency_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableFeignClients
/*@EnableDiscoveryClient*/
public class EmergencySchedulerApplication {
    @Bean
    public RestTemplate getRestTemplate(){
        return  new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EmergencySchedulerApplication.class, args);
    }

}
