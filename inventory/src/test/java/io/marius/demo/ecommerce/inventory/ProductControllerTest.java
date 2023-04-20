package io.marius.demo.ecommerce.inventory;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import org.flywaydb.core.Flyway;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
class ProductControllerTest {

  @Autowired private Flyway flyway;

  @Autowired private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    flyway.clean();
    flyway.migrate();
  }

  @Test
  void shouldReturnProductWhenProductIdExists() throws Exception {
    String findProductByIdQuery =
        new JSONObject(
                "{"
                    + "  \"query\": \"query{ product(id:1) { id name price description productCategory { id name } properties { id name description } }}\"\n"
                    + "}")
            .toString();

    MvcResult mvcResult = buildMvcResult(findProductByIdQuery);

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.product.id").value(1))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.product.name").value("Apple MacBook Air (M2)"))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.product.properties", isA(ArrayList.class)))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.product.properties[0].name").value("Color"));
  }

  @Test
  void shouldNotReturnProductWhenProductIdNotExists() throws Exception {
    String findProductByNonExistingIdQuery =
        new JSONObject("{" + "  \"query\": \"query{ product(id:999) { id name }}\"\n" + "}")
            .toString();

    MvcResult mvcResult = buildMvcResult(findProductByNonExistingIdQuery);

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors", isA(ArrayList.class)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)));
  }

  @Test
  void shouldReturnProductListWhenFilterValid() throws Exception {
    String findProductsByNameAndPropertyQuery =
        new JSONObject(
                "{"
                    + "  \"query\": \"query{ allProducts( filter : { name: \\\"Apple\\\" properties: { name: \\\"Color\\\" } } ) { id name price description productCategory { id name } properties { id name description } }}\"\n"
                    + "}")
            .toString();

    MvcResult mvcResult = buildMvcResult(findProductsByNameAndPropertyQuery);

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.allProducts", isA(ArrayList.class)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.allProducts", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.allProducts[0].id").value(1));
  }

  @Test
  void shouldReturnEmptyProductListWhenFilterNotValid() throws Exception {
    String findProductsByNonExistingNameQuery =
        new JSONObject(
                "{"
                    + "  \"query\": \"query{ allProducts( filter : { name: \\\"Lenovo\\\" } ) { id name price description productCategory { id name } properties { id name description } }}\"\n"
                    + "}")
            .toString();

    MvcResult mvcResult = buildMvcResult(findProductsByNonExistingNameQuery);

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.allProducts", isA(ArrayList.class)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.allProducts", hasSize(0)));
  }

  @Test
  void shouldCreateProductWhenInputIsValid() throws Exception {
    String createProductQuery =
        new JSONObject(
                "{"
                    + "  \"query\": \"mutation createProduct{ createProduct(product : { name: \\\"TestOne\\\" price: 1000.0 description: \\\"Test description\\\" productCategory: \\\"Laptop\\\" properties: [ { name: \\\"Hello\\\", description: \\\"World\\\" } ] })}\"\n"
                    + "}")
            .toString();

    MvcResult mvcResult = buildMvcResult(createProductQuery);

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.createProduct")
                .value("Successfully saved product with id: 10"));
  }

  @Test
  void shouldUpdateProductWhenInputIsValid() throws Exception {
    String updateProductQuery =
        new JSONObject(
                "{"
                    + "  \"query\": \"mutation createProduct{ createProduct(product : { id : 1 name: \\\"TestOne\\\" price: 1000.0 description: \\\"Test description\\\" productCategory: \\\"Laptop\\\" properties: [ { id: 1, name: \\\"Hello\\\", description: \\\"World\\\" } ] })}\"\n"
                    + "}")
            .toString();

    MvcResult mvcResult = buildMvcResult(updateProductQuery);

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.createProduct")
                .value("Successfully saved product with id: 1"));
  }

  private MvcResult buildMvcResult(String query) throws Exception {
    return mockMvc
        .perform(post("/graphql").content(query).contentType(MediaType.APPLICATION_JSON))
        .andExpect(request().asyncStarted())
        .andExpect(request().asyncResult(notNullValue()))
        .andReturn();
  }
}
