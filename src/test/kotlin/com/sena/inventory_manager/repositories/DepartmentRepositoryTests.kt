package com.sena.inventory_manager.repositories

import com.sena.inventory_manager.entities.Department
import com.sena.inventory_manager.entities.DepartmentRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class DepartmentRepositoryTests(
    @Autowired val repository: DepartmentRepository
) {

    @Test
    fun `Test post department`(){
        val savedCity = repository.save(Department(description = "New Department"))

        Assertions.assertThat(savedCity).isNotNull
        Assertions.assertThat(savedCity.id).isGreaterThan(0)
    }

    @Test
    fun `Test get all departments`(){
        val department1 = Department(description = "A Department")
        val department2 = Department(description = "Yet Another Department")

        repository.save(department1)
        repository.save(department2)
        val departmentList = repository.findAll()

        println(departmentList)

        Assertions.assertThat(departmentList).isNotNull
        Assertions.assertThat(departmentList.size).isEqualTo(2)
    }

    @Test
    fun `Test get department by id`(){
        val department = repository.save(Department(description = "New Department"))

        val returnedDepartment = repository.findById(department.id!!).get()

        Assertions.assertThat(returnedDepartment).isNotNull
    }

    @Test
    fun `Test put department`(){
        val department = repository.save(Department(description = "New Department"))

        val foundDepartment = repository.findById(department.id!!).get()
        foundDepartment.description = "Department Name"

        val updatedDepartment = repository.save(foundDepartment)

        Assertions.assertThat(updatedDepartment).isNotNull
        Assertions.assertThat(updatedDepartment.id).isEqualTo(department.id)
        Assertions.assertThat(updatedDepartment.description).isEqualTo("Department Name")
    }

    @Test
    fun `Test delete department by id`(){
        val city = repository.save(Department(description = "New Department"))

        repository.deleteById(city.id!!)

        val cityReturn = repository.findById(city.id!!)

        Assertions.assertThat(cityReturn).isEmpty
    }
}