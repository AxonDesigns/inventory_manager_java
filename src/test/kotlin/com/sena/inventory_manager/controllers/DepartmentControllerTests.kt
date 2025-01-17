package com.sena.inventory_manager.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.sena.inventory_manager.entities.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

@WebMvcTest(DepartmentController::class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension::class)
class DepartmentControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: DepartmentService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var department: Department
    private lateinit var departments: List<Department>

    @BeforeEach
    fun init(){
        department = Department(description = "New Department")
        departments = listOf(Department(description = "Hello"), Department(description = "World"))
    }

    @Test
    fun `Test department creation`(){
        `when`(service.new(department)).thenReturn(department)

        val response = mockMvc.perform(
            post("/department")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(department))
        )

        response.andExpect(MockMvcResultMatchers.status().isCreated).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test department list`(){
        `when`(service.findAll()).thenReturn(departments)

        val response = mockMvc.perform(
            get("/department")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(departments))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test department update`(){
        `when`(service.update(department, 1L)).thenReturn(department)

        val response = mockMvc.perform(put("/department/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(department))
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test department delete`(){
        val response = mockMvc.perform(delete("/department/1")
            .contentType(MediaType.APPLICATION_JSON)
        )

        response.andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }
}