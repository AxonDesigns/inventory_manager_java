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

@WebMvcTest(ProductTypeController::class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension::class)
class ProductTypeControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: ProductTypeService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var productType: ProductType
    private lateinit var productTypes: List<ProductType>

    @BeforeEach
    fun init(){
        productType = ProductType(description = "New Product Type")
        productTypes = listOf(ProductType(description = "Hello"), ProductType(description = "World"))
    }

    @Test
    fun `Test product type creation`(){
        `when`(service.new(productType)).thenReturn(productType)

        val response = mockMvc.perform(
            post("/product_type")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productType))
        )

        response.andExpect(MockMvcResultMatchers.status().isCreated).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product type list`(){
        `when`(service.findAll()).thenReturn(productTypes)

        val response = mockMvc.perform(
            get("/product_type")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productTypes))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product type update`(){
        `when`(service.update(productType, 1L)).thenReturn(productType)

        val response = mockMvc.perform(put("/product_type/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productType))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product type delete`(){
        val response = mockMvc.perform(delete("/product_type/1")
            .contentType(MediaType.APPLICATION_JSON)
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }
}