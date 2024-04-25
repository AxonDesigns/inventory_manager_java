package com.sena.inventory_manager.services

import com.sena.inventory_manager.entities.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class DepartmentServiceTests{
    @Mock
    private lateinit var repository: DepartmentRepository
    @InjectMocks
    private lateinit var service: DepartmentService

    private lateinit var department: Department
    private lateinit var departments: List<Department>

    @BeforeEach
    fun init(){
        department = Department(description = "New Department")
        departments = listOf(Department(description = "Hello"), Department(description = "World"))
    }

    @Test
    fun `Test create new Department`(){
        Mockito.`when`(repository.save(Mockito.any(Department::class.java))).thenReturn(department)

        val savedDepartment = service.new(department)
        Assertions.assertThat(savedDepartment).isNotNull
    }

    @Test
    fun `Test get all cities`(){
        Mockito.`when`(repository.findAll()).thenReturn(departments)

        val response = service.findAll()

        Assertions.assertThat(response).isNotNull
    }

    @Test
    fun `Test get Department by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(department))

        val savedDepartment = service.findById(1)
        Assertions.assertThat(savedDepartment).isNotNull
    }

    @Test
    fun `Test update Department by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(department))
        Mockito.`when`(repository.save(Mockito.any(Department::class.java))).thenReturn(department)

        val savedDepartment = service.update(department, 1)
        Assertions.assertThat(savedDepartment).isNotNull
    }

    @Test
    fun `Test delete Department by id`(){
        assertAll({ service.delete(1) })
    }
}