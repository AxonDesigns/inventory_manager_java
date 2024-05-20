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

@WebMvcTest(LocationController::class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension::class)
class LocationControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: LocationService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var location: Location
    private lateinit var locations: List<Location>
    private lateinit var locationRequest: LocationRequest
    private lateinit var department: Department
    private lateinit var city: City
    private lateinit var locationType: LocationType

    @BeforeEach
    fun init(){
        locationType = LocationType(description = "New Location Type")
        department = Department(description = "New Department")
        city = City(description = "New City")

        location = Location(
            description = "New Location",
            city = city,
            department = department,
            locationType = locationType
        )
        locations = listOf(
            Location(
                description = "New Location",
                city = city,
                department = department,
                locationType = locationType
            ),
            Location(
                description = "New Location",
                city = city,
                department = department,
                locationType = locationType
            )
        )
        locationRequest = LocationRequest(
            description = "New Location",
            city = 1L,
            department = 1L,
            locationType = 1L
        )
    }

    @Test
    fun `Test location creation`(){
        `when`(service.new(locationRequest)).thenReturn(location)

        val response = mockMvc.perform(
            post("/location")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(locationRequest))
        )

        response.andExpect(MockMvcResultMatchers.status().isCreated).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test location list`(){
        `when`(service.findAll()).thenReturn(locations)

        val response = mockMvc.perform(
            get("/location")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(locations))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test location update`(){
        `when`(service.update(locationRequest, 1L)).thenReturn(location)

        val response = mockMvc.perform(put("/location/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(locationRequest))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test location delete`(){
        val response = mockMvc.perform(delete("/location/1")
            .contentType(MediaType.APPLICATION_JSON)
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }
}