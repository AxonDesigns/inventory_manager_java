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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@WebMvcTest(MeasureUnitController::class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension::class)
class MeasureUnitControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: MeasureUnitService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var measureUnit: MeasureUnit
    private lateinit var measureUnits: List<MeasureUnit>

    @BeforeEach
    fun init(){
        measureUnit = MeasureUnit(description = "New Measure Unit")
        measureUnits = listOf(MeasureUnit(description = "Hello"), MeasureUnit(description = "World"))
    }

    @Test
    fun `Test measure unit creation`(){
        `when`(service.new(measureUnit)).thenReturn(measureUnit)

        val response = mockMvc.perform(
            post("/measure_unit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(measureUnit))
        )

        response.andExpect(MockMvcResultMatchers.status().isCreated).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test measure unit list`(){
        `when`(service.findAll()).thenReturn(measureUnits)

        val response = mockMvc.perform(
            get("/measure_unit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(measureUnits))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test measure unit update`(){
        `when`(service.update(measureUnit, 1L)).thenReturn(measureUnit)

        val response = mockMvc.perform(put("/measure_unit/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(measureUnit))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test measure unit delete`(){
        val response = mockMvc.perform(delete("/measure_unit/1")
            .contentType(MediaType.APPLICATION_JSON)
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }
}