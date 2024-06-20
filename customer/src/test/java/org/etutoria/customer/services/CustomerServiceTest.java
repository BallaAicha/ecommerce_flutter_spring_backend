package org.etutoria.customer.services;

import org.etutoria.customer.customers.Address;
import org.etutoria.customer.customers.Customer;
import org.etutoria.customer.dto.CustomerRequest;
import org.etutoria.customer.dto.CustomerResponse;
import org.etutoria.customer.exception.CustomerNotFoundException;
import org.etutoria.customer.mappers.CustomerMapper;
import org.etutoria.customer.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository mockRepository;
    @Mock
    private CustomerMapper mockMapper;

    private CustomerService customerServiceUnderTest;

    @BeforeEach
    void setUp() {
        customerServiceUnderTest = new CustomerService(mockRepository, mockMapper);
    }

    @Test
    void testCreateCustomer() {
        // Setup
        final CustomerRequest request = new CustomerRequest("id", "firstname", "lastname", "email",
                Address.builder().build());
        final ResponseEntity<Map<String, String>> expectedResult = new ResponseEntity<>(
                Map.ofEntries(Map.entry("value", "value")), HttpStatus.OK);

        // Configure CustomerMapper.toCustomer(...).
        final Customer customer = Customer.builder()
                .id("id")
                .firstname("firstname")
                .email("email")
                .address(Address.builder().build())
                .build();
        when(mockMapper.toCustomer(
                new CustomerRequest("id", "firstname", "lastname", "email", Address.builder().build())))
                .thenReturn(customer);

        // Configure CustomerRepository.save(...).
        final Customer customer1 = Customer.builder()
                .id("id")
                .firstname("firstname")
                .email("email")
                .address(Address.builder().build())
                .build();
        when(mockRepository.save(any(Customer.class))).thenReturn(customer1);

        // Run the test
        final ResponseEntity<Map<String, String>> result = customerServiceUnderTest.createCustomer(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateCustomer() {
        // Setup
        final CustomerRequest request = new CustomerRequest("id", "firstname", "lastname", "email",
                Address.builder().build());

        // Configure CustomerRepository.findById(...).
        final Optional<Customer> customer = Optional.of(Customer.builder()
                .id("id")
                .firstname("firstname")
                .email("email")
                .address(Address.builder().build())
                .build());
        when(mockRepository.findById("id")).thenReturn(customer);

        // Run the test
        customerServiceUnderTest.updateCustomer(request);

        // Verify the results
        verify(mockRepository).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_CustomerRepositoryFindByIdReturnsAbsent() {
        // Setup
        final CustomerRequest request = new CustomerRequest("id", "firstname", "lastname", "email",
                Address.builder().build());
        when(mockRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> customerServiceUnderTest.updateCustomer(request))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void testFindAllCustomers() {
        // Setup
        final List<CustomerResponse> expectedResult = List.of(
                new CustomerResponse("id", "firstname", "lastname", "email", Address.builder().build()));

        // Configure CustomerRepository.findAll(...).
        final List<Customer> customers = List.of(Customer.builder()
                .id("id")
                .firstname("firstname")
                .email("email")
                .address(Address.builder().build())
                .build());
        when(mockRepository.findAll()).thenReturn(customers);

        // Configure CustomerMapper.fromCustomer(...).
        final CustomerResponse customerResponse = new CustomerResponse("id", "firstname", "lastname", "email",
                Address.builder().build());
        when(mockMapper.fromCustomer(any(Customer.class))).thenReturn(customerResponse);

        // Run the test
        final List<CustomerResponse> result = customerServiceUnderTest.findAllCustomers();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindAllCustomers_CustomerRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<CustomerResponse> result = customerServiceUnderTest.findAllCustomers();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindById() {
        // Setup
        final CustomerResponse expectedResult = new CustomerResponse("id", "firstname", "lastname", "email",
                Address.builder().build());

        // Configure CustomerRepository.findById(...).
        final Optional<Customer> customer = Optional.of(Customer.builder()
                .id("id")
                .firstname("firstname")
                .email("email")
                .address(Address.builder().build())
                .build());
        when(mockRepository.findById("id")).thenReturn(customer);

        // Configure CustomerMapper.fromCustomer(...).
        final CustomerResponse customerResponse = new CustomerResponse("id", "firstname", "lastname", "email",
                Address.builder().build());
        when(mockMapper.fromCustomer(any(Customer.class))).thenReturn(customerResponse);

        // Run the test
        final CustomerResponse result = customerServiceUnderTest.findById("id");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById_CustomerRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> customerServiceUnderTest.findById("id")).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void testExistsById() {
        // Setup
        // Configure CustomerRepository.findById(...).
        final Optional<Customer> customer = Optional.of(Customer.builder()
                .id("id")
                .firstname("firstname")
                .email("email")
                .address(Address.builder().build())
                .build());
        when(mockRepository.findById("id")).thenReturn(customer);

        // Run the test
        final boolean result = customerServiceUnderTest.existsById("id");

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testExistsById_CustomerRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final boolean result = customerServiceUnderTest.existsById("id");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testDeleteCustomer() {
        // Setup
        // Run the test
        customerServiceUnderTest.deleteCustomer("id");

        // Verify the results
        verify(mockRepository).deleteById("id");
    }
}
