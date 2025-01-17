package com.sena.inventory_manager.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.sena.inventory_manager.entities.City
import com.sena.inventory_manager.entities.CityController
import com.sena.inventory_manager.entities.CityService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(CityController::class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension::class)
class CityControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: CityService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var city: City
    private lateinit var cities: List<City>

    @BeforeEach
    fun init(){
        city = City(description = "New City")
        cities = listOf(City(description = "Hello"), City(description = "World"))
    }

    @Test
    fun `Test city creation`(){
         `when`(service.new(city)).thenReturn(city)

        val response = mockMvc.perform(post("/city")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(city))
        )

        response.andExpect(MockMvcResultMatchers.status().isCreated).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test city list`(){
        `when`(service.findAll()).thenReturn(cities)

        val response = mockMvc.perform(get("/city")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cities))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test city update`(){
        `when`(service.update(city, 1L)).thenReturn(city)

        val response = mockMvc.perform(put("/city/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(city))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test city delete`(){
        val response = mockMvc.perform(delete("/city/1")
            .contentType(MediaType.APPLICATION_JSON)
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }
}