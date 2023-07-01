package io.marius.demo.ecommerce.inventory.cucumber.glue;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.marius.demo.ecommerce.inventory.service.S3Service;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.flywaydb.core.Flyway;
import org.json.JSONObject;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
@WithMockUser(roles = "USER")
@AutoConfigureMockMvc
@CucumberContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CucumberSteps {
  @MockBean S3Service s3Service;
  @Autowired private Flyway flyway;
  @Autowired private MockMvc mockMvc;
  private ResultActions mvcResult;
  private Map<String, Object> propertyMap;

  @Before
  public void setUp() {
    propertyMap = new HashMap<>();
    flyway.migrate();
  }

  @Given("property with key as {string} and value as {string}")
  public void givenPropertyAsString(String key, String value) {
    propertyMap.put(key, value);
  }

  @Given("property with key as {string} and value as {float}")
  public void givenPropertyAsDouble(String key, float value) {
    propertyMap.put(key, value);
  }

  @When("the client calls allProducts query")
  public void whenClientCalls() throws Exception {
    String query =
        String.format(
            "{"
                + "  \"query\": \"query{ allProducts( filter : { %s } ) { id name price description productCategory { id name } properties { id name description } productFiles { id key s3Url} }}\"\n"
                + "}",
            stringifyPropertyMap());

    this.mvcResult =
        mockMvc
            .perform(asyncDispatch(buildMvcResult(new JSONObject(query).toString())))
            .andDo(print());
  }

  @Then("response status code is {int}")
  public void thenStatusCode(int status) throws Exception {
    mvcResult.andExpect(status().is(status));
  }

  @Then("returned key as {string} has size as {int}")
  public void returnedKeyHasSize(String key, int size) throws Exception {
    mvcResult.andExpect(MockMvcResultMatchers.jsonPath(key, hasSize(size)));
  }

  @Then("returned key as {string} should be as {string}")
  public void returnedKeyHasValueAsString(String key, String value) throws Exception {
    mvcResult.andExpect(MockMvcResultMatchers.jsonPath(key).value(value));
  }

  private MvcResult buildMvcResult(String query) throws Exception {
    return mockMvc
        .perform(post("/graphql").content(query).contentType(MediaType.APPLICATION_JSON))
        .andExpect(request().asyncStarted())
        .andExpect(request().asyncResult(notNullValue()))
        .andReturn();
  }

  private String stringifyPropertyMap() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
      if (entry.getValue() instanceof String) {
        result.append(String.format("%s: \\\"%s\\\"", entry.getKey(), entry.getValue()));
      }
      if (entry.getValue() instanceof Float) {
        result.append(String.format(Locale.US, "%s: %.2f", entry.getKey(), entry.getValue()));
      }
      result.append(" ");
    }

    return result.toString();
  }
}
