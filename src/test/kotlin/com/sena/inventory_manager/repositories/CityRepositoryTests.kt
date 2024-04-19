package com.sena.inventory_manager.repositories

import com.sena.inventory_manager.entities.City
import com.sena.inventory_manager.entities.CityRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CityRepositoryTests(
    @Autowired val repository: CityRepository
) {

    @Test
    fun `Test post city`() {
        val savedCity = repository.save(City(description = "New City"))

        Assertions.assertThat(savedCity).isNotNull
        Assertions.assertThat(savedCity.id).isGreaterThan(0)
    }

    @Test
    fun `Test get all cities`(){
        val city1 = City(description = "A City")
        val city2 = City(description = "Yet Another City")

        repository.save(city1)
        repository.save(city2)
        val cityList = repository.findAll()

        println(cityList)

        Assertions.assertThat(cityList).isNotNull
        Assertions.assertThat(cityList.size).isEqualTo(2)
    }

    @Test
    fun `Test get city by id`(){
        val city = repository.save(City(description = "New City"))

        val returnedCity = repository.findById(city.id!!).get()

        Assertions.assertThat(returnedCity).isNotNull
    }

    @Test
    fun `Test put city`(){
        val city = repository.save(City(description = "New City"))

        val foundCity = repository.findById(city.id!!).get()
        foundCity.description = "City Name"

        val updatedCity = repository.save(foundCity)

        Assertions.assertThat(updatedCity).isNotNull
        Assertions.assertThat(updatedCity.id).isEqualTo(city.id)
        Assertions.assertThat(updatedCity.description).isEqualTo("City Name")
    }

    @Test
    fun `Test delete city by id`(){
        val city = repository.save(City(description = "New City"))

        repository.deleteById(city.id!!)

        val cityReturn = repository.findById(city.id!!)

        Assertions.assertThat(cityReturn).isEmpty
    }
}