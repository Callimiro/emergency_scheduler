package com.iedss_local_emergency_scheduler.emergency_scheduler.proxies;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservice-notification-cache")
public interface MicroserviceNotificationCache {

}
