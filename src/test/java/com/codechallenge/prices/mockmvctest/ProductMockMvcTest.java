package com.codechallenge.prices.mockmvctest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codechallenge.Application;
import com.codechallenge.prices.bo.internal.ProductBO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest(
    classes = Application.class)
@TestPropertySource(
    locations = "classpath:application-integration-test.properties")
@AutoConfigureMockMvc
@ActiveProfiles({"test"})

class ProductMockMvcTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String PRODUCT_ID = "35460";
  private static final String PRODUCT_NAME = "ZAPATOS DE CUERO";
  private static final String JSON_ROOT = "$";

  @Test
  @DisplayName("test que comprueba la grabación de un producto")
  void test1() throws Exception {
    mvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(ProductBO.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .build()))
    )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath(JSON_ROOT + ".callData.message", is("ok")));
  }

  private String asJsonString(final Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
