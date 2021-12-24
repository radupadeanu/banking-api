package com.ing.banking.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ing.banking.dto.CustomerDTO;
import com.ing.banking.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {

  @Autowired private WebApplicationContext webApplicationContext;

  @MockBean private CustomerService customerService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  void getAllCustomersWithInactiveAccounts() throws Exception {

    this.mockMvc.perform(get("/getCustomers")).andExpect(status().isOk());
  }

  @Test
  void addCustomer() throws Exception {

    CustomerDTO customerDTO = new CustomerDTO();

    String requestJson = getSerializedObject(customerDTO);

    this.mockMvc
        .perform(post("/addCustomer").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  void updateCustomer() throws Exception {
    CustomerDTO customerDTO = new CustomerDTO();

    String requestJson = getSerializedObject(customerDTO);

    this.mockMvc
        .perform(
            put("/updateCustomer").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  void getSavingsAmount() throws Exception {
    String ssn = "ssn";

    this.mockMvc.perform(get("/getSavingsAmount").param("ssn", ssn)).andExpect(status().isOk());
  }

  private String getSerializedObject(CustomerDTO customerDTO) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(customerDTO);
  }
}
