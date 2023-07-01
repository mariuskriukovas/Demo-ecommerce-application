package io.marius.demo.ecommerce.common.validation.config;

import io.marius.demo.ecommerce.common.validation.exceptionhandler.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({GlobalExceptionHandler.class})
public class AutoConfiguration {}
