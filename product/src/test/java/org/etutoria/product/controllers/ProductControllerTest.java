package org.etutoria.product.controllers;

import org.etutoria.product.dto.ProductResponse;
import org.etutoria.product.services.ImageService;
import org.etutoria.product.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService mockService;
    @MockBean
    private ImageService mockImageService;

    @Test
    void testFindByName() throws Exception {
        // Setup
        // Configure ProductService.findByName(...).
        final List<ProductResponse> productResponses = List.of(
                new ProductResponse(0, "name", "description", 0.0, new BigDecimal("0.00"), 0, "categoryName",
                        "categoryDescription"));
        when(mockService.findByName("search")).thenReturn(productResponses);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/products/name/{search}", "search")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFindByName_ProductServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockService.findByName("search")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/products/name/{search}", "search")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
