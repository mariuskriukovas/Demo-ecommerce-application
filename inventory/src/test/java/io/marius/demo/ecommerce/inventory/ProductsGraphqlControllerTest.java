package io.marius.demo.ecommerce.inventory;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.marius.demo.ecommerce.inventory.service.S3Service;
import java.util.ArrayList;
import org.flywaydb.core.Flyway;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductsGraphqlControllerTest {
  @MockBean S3Service s3Service;

  @Autowired private Flyway flyway;

  @Autowired private MockMvc mockMvc;

  @BeforeAll
  public void setUp() {
    flyway.migrate();
  }

  @Test
  void shouldReturnProductWhenProductIdExists() throws Exception {
    String findProductByIdQuery =
        new JSONObject(
                "{"
                    + "  \"query\": \"query{ product(id:1) { id name price description productCategory { id name } properties { id name description } productFiles { id key s3Url}  }}\"\n"
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
            MockMvcResultMatchers.jsonPath("$.data.product.properties[0].name").value("Color"))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.product.productFiles", isA(ArrayList.class)))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.product.productFiles[0].s3Url")
                .value(
                    "https://marius-image-storage.s3.eu-north-1.amazonaws.com/sorry_not_found.jpg"));
  }

  @Test
  void shouldReturnProductListWhenFilterValid() throws Exception {
    String findProductsByNameAndPropertyQuery =
        new JSONObject(
                "{"
                    + "  \"query\": \"query{ allProducts( filter : { name: \\\"Apple\\\" description: \\\"MacBook Air\\\" category: \\\"Laptop\\\" priceFrom: 1000 priceTo: 2000 properties: { name: \\\"Color\\\" } } ) { id name price description productCategory { id name } properties { id name description } productFiles { id key s3Url} }}\"\n"
                    + "}")
            .toString();

    MvcResult mvcResult = buildMvcResult(findProductsByNameAndPropertyQuery);

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.allProducts", isA(ArrayList.class)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.allProducts", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.allProducts[0].id").value(1))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.allProducts[0].name")
                .value("Apple MacBook Air (M2)"))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.data.allProducts[0].properties", isA(ArrayList.class)))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.allProducts[0].properties[0].name")
                .value("Color"))
        .andExpect(
            MockMvcResultMatchers.jsonPath(
                "$.data.allProducts[0].productFiles", isA(ArrayList.class)))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.allProducts[0].productFiles[0].s3Url")
                .value(
                    "https://marius-image-storage.s3.eu-north-1.amazonaws.com/sorry_not_found.jpg"));
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

  private MvcResult buildMvcResult(String query) throws Exception {
    return mockMvc
        .perform(post("/graphql").content(query).contentType(MediaType.APPLICATION_JSON))
        .andExpect(request().asyncStarted())
        .andExpect(request().asyncResult(notNullValue()))
        .andReturn();
  }
}
