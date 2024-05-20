package com.sena.inventory_manager.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.sena.inventory_manager.entities.LocationType
import com.sena.inventory_manager.entities.LocationTypeController
import com.sena.inventory_manager.entities.LocationTypeService
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

@WebMvcTest(LocationTypeController::class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension::class)
class LocationTypeControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: LocationTypeService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var locationType: LocationType
    private lateinit var locationTypes: List<LocationType>

    @BeforeEach
    fun init(){
        locationType = LocationType(description = "New Location Type")
        locationTypes = listOf(LocationType(description = "Hello"), LocationType(description = "World"))
    }

    @Test
    fun `Test location type creation`(){
        `when`(service.new(locationType)).thenReturn(locationType)

        val response = mockMvc.perform(
            post("/location_type")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(locationType))
        )

        response.andExpect(MockMvcResultMatchers.status().isCreated).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test location type list`(){
        `when`(service.findAll()).thenReturn(locationTypes)

        val response = mockMvc.perform(
            get("/location_type")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(locationTypes))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test location type update`(){
        `when`(service.update(locationType, 1L)).thenReturn(locationType)

        val response = mockMvc.perform(put("/location_type/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(locationType))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test location type delete`(){
        val response = mockMvc.perform(delete("/location_type/1")
            .contentType(MediaType.APPLICATION_JSON)
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }
}