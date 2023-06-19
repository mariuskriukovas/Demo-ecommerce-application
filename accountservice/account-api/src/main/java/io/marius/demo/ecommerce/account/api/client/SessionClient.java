package io.marius.demo.ecommerce.account.api.client;

import io.marius.demo.ecommerce.account.api.resource.SessionResource;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "sessionClient", url = "${services.account.url}" + "/sessions")
public interface SessionClient extends SessionResource {}
