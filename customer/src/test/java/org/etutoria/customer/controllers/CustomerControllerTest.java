package org.etutoria.customer.controllers;

import org.etutoria.customer.customers.Address;
import org.etutoria.customer.dto.CustomerRequest;
import org.etutoria.customer.dto.CustomerResponse;
import org.etutoria.customer.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService mockService;

    @Test
    void testCreateCustomer() throws Exception {
        // Setup
        // Configure CustomerService.createCustomer(...).
        final ResponseEntity<Map<String, String>> mapResponseEntity = new ResponseEntity<>(
                Map.ofEntries(Map.entry("value", "value")), HttpStatus.OK);
        when(mockService.createCustomer(
                new CustomerRequest("id", "firstname", "lastname", "email", Address.builder().build())))
                .thenReturn(mapResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/customers")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateCustomer() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/v1/customers")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockService).updateCustomer(
                new CustomerRequest("id", "firstname", "lastname", "email", Address.builder().build()));
    }

    @Test
    void testFindAll() throws Exception {
        // Setup
        // Configure CustomerService.findAllCustomers(...).
        final List<CustomerResponse> customerResponses = List.of(
                new CustomerResponse("id", "firstname", "lastname", "email", Address.builder().build()));
        when(mockService.findAllCustomers()).thenReturn(customerResponses);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFindAll_CustomerServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockService.findAllCustomers()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testExistsById() throws Exception {
        // Setup
        when(mockService.existsById("customerId")).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/customers/exists/{customer-id}", "customerId")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFindById() throws Exception {
        // Setup
        // Configure CustomerService.findById(...).
        final CustomerResponse customerResponse = new CustomerResponse("id", "firstname", "lastname", "email",
                Address.builder().build());
        when(mockService.findById("customerId")).thenReturn(customerResponse);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/customers/{customer-id}", "customerId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/customers/{customer-id}", "customerId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockService).deleteCustomer("customerId");
    }
}
