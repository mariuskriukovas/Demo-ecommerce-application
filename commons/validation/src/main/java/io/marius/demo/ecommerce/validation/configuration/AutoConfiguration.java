package io.marius.demo.ecommerce.validation.configuration;

import io.marius.demo.ecommerce.validation.exceptionhandler.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({GlobalExceptionHandler.class})
public class AutoConfiguration {}
