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

@WebMvcTest(ProductStateController::class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension::class)
class ProductStateControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: ProductStateService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var productState: ProductState
    private lateinit var productStates: List<ProductState>

    @BeforeEach
    fun init(){
        productState = ProductState(description = "New Product State")
        productStates = listOf(ProductState(description = "Hello"), ProductState(description = "World"))
    }

    @Test
    fun `Test product state creation`(){
        `when`(service.new(productState)).thenReturn(productState)

        val response = mockMvc.perform(
            post("/product_state")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productState))
        )

        response.andExpect(MockMvcResultMatchers.status().isCreated).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product state list`(){
        `when`(service.findAll()).thenReturn(productStates)

        val response = mockMvc.perform(
            get("/product_state")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productStates))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product state update`(){
        `when`(service.update(productState, 1L)).thenReturn(productState)

        val response = mockMvc.perform(put("/product_state/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productState))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test product state delete`(){
        val response = mockMvc.perform(delete("/product_state/1")
            .contentType(MediaType.APPLICATION_JSON)
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }
}