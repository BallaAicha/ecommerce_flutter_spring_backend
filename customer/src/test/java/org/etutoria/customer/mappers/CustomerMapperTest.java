package org.etutoria.customer.mappers;

import org.etutoria.customer.customers.Address;
import org.etutoria.customer.customers.Customer;
import org.etutoria.customer.dto.CustomerRequest;
import org.etutoria.customer.dto.CustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private CustomerMapper customerMapperUnderTest;

    @BeforeEach
    void setUp() {
        customerMapperUnderTest = new CustomerMapper();
    }

    @Test
    void testToCustomer() {
        // Setup
        final CustomerRequest request = new CustomerRequest("id", "firstname", "lastname", "email",
                Address.builder().build());

        // Run the test
        final Customer result = customerMapperUnderTest.toCustomer(request);

        // Verify the results
    }

    @Test
    void testFromCustomer() {
        // Setup
        final Customer customer = Customer.builder()
                .id("id")
                .firstname("firstname")
                .lastname("lastname")
                .email("email")
                .address(Address.builder().build())
                .build();
        final CustomerResponse expectedResult = new CustomerResponse("id", "firstname", "lastname", "email",
                Address.builder().build());

        // Run the test
        final CustomerResponse result = customerMapperUnderTest.fromCustomer(customer);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
