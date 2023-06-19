package io.marius.demo.ecommerce.inventory.api.client;

import io.marius.demo.ecommerce.inventory.api.resource.ClassifiersResource;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "classifiersClient", url = "${services.inventory.url}" + "/api/classifiers")
public interface ClassifiersClient extends ClassifiersResource {}
