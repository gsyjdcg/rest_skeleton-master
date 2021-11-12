package com.codechallenge.prices.mockmvctest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codechallenge.Application;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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

class PriceMockMvcTest {
  @Autowired
  private MockMvc mvc;

  private static final String PRODUCT_ID = "35457";
  private static final int BRAND_ID = 1;
  private static final String JSON_ROOT = "$";

  @ParameterizedTest(name = "test que evalua el precio {0} del artículo en la fecha {1}")
  @CsvFileSource(resources = "/testdata/controllertest.csv", numLinesToSkip = 1)
  void test1(String price, String date) throws Exception {

    mvc.perform(get("/price")
        .queryParam("applicationDate", date)
        .queryParam("productId", PRODUCT_ID)
        .queryParam("brandId", String.valueOf(BRAND_ID))
    )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath(JSON_ROOT + ".finalPrice", is(price)))
        .andExpect(jsonPath(JSON_ROOT + ".productId", is(PRODUCT_ID)))
        .andExpect(jsonPath(JSON_ROOT + ".brandId", is(BRAND_ID)))
    ;
  }

  @Test
  @DisplayName("test que comprueba el error cuando no encuentra precio para un artículo")
  void test6() throws Exception {
    mvc.perform(get("/price")
        .queryParam("applicationDate", "2023-06-15-00:00:00")
        .queryParam("productId", PRODUCT_ID)
        .queryParam("brandId", String.valueOf(BRAND_ID))
    )
        .andExpect(status().isNotFound());

  }

  @Test
  @DisplayName("test que comprueba la devolución de todos los precios")
  void test7() throws Exception {

    List<String> products = Arrays.asList(PRODUCT_ID, PRODUCT_ID, PRODUCT_ID, PRODUCT_ID);
    mvc.perform(get("/price/all")
        .queryParam("productId", PRODUCT_ID)
    )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath(JSON_ROOT , hasSize(4)))
        .andExpect(jsonPath(JSON_ROOT + "[*].productId", is(products)))
        .andExpect(jsonPath(JSON_ROOT + "[0].startDate", is("2020-06-14-00:00:00")))
        .andExpect(jsonPath(JSON_ROOT + "[1].startDate", is("2020-06-14-15:00:00")))
        .andExpect(jsonPath(JSON_ROOT + "[2].startDate", is("2020-06-15-00:00:00")))
        .andExpect(jsonPath(JSON_ROOT + "[3].startDate", is("2020-06-15-16:00:00")));
  }

}
