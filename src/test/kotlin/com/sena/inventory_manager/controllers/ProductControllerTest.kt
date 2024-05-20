package com.sena.inventory_manager.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.sena.inventory_manager.entities.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(ProductController::class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension::class)
class ProductControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: ProductService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var product: Product
    private lateinit var productRequest: ProductRequest
    private lateinit var products: List<Product>
    private lateinit var productType: ProductType
    private lateinit var measureUnit: MeasureUnit

    @BeforeEach
    fun init(){
        productType = ProductType(description = "New Product Type")
        measureUnit = MeasureUnit(description = "New MeasureUnit")

        product = Product(
            name = "New Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
        )
        products = listOf(
            Product(
            name = "New Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
            ),
            Product(
            name = "New Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
            )
        )
        productRequest = ProductRequest(
            name = "New Product",
            description = "New Product Description",
            productTypeId = 1L,
            measureUnitId = 1L,
            unitPrice = 1L,
            unitReference = 1L
        )
    }

    @Test
    fun `Test product creation`(){
        `when`(service.new(productRequest)).thenReturn(product)

        val response = mockMvc.perform(
            post("/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product))
        )

        response.andExpect(MockMvcResultMatchers.status().isCreated).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product list`(){
        `when`(service.findAll()).thenReturn(products)

        val response = mockMvc.perform(
            get("/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(products))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product update`(){
        `when`(service.update(productRequest, 1L)).thenReturn(product)

        val response = mockMvc.perform(put("/product/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product delete`() {
        val response = mockMvc.perform(
            delete("/product/1")
                .contentType(MediaType.APPLICATION_JSON)
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }
}