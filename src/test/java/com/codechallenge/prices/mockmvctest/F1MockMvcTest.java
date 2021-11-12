package com.codechallenge.prices.mockmvctest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codechallenge.Application;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class F1MockMvcTest {

  @Autowired
  private MockMvc mvc;
  private static final String JSON_ROOT = "$";

  @ParameterizedTest(name = "test que evalua que {0} tiene nacionalidad {1}")
  @CsvFileSource(resources = "/testdata/f1-drivers.csv", numLinesToSkip = 1)
  void test1(String name, String nationality) throws Exception {


    mvc.perform(get("/f1-driver")
        .queryParam("driver", name)
    )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath(JSON_ROOT + ".nationality", is(nationality)))
    ;
  }

}
