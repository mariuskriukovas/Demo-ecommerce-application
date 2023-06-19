package io.marius.demo.ecommerce.inventory.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src/test/resources/features"},
    plugin = {"pretty"},
    glue = {"io.marius.demo.ecommerce.inventory.cucumber.glue"})
public class CucumberIntegrationTest {}
