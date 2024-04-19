package com.sena.inventory_manager.services

import com.sena.inventory_manager.entities.City
import com.sena.inventory_manager.entities.CityRepository
import com.sena.inventory_manager.entities.CityService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
class CityServiceTests {
    @Mock
    private lateinit var repository: CityRepository
    @InjectMocks
    private lateinit var service: CityService

    private lateinit var city: City
    private lateinit var cities: List<City>

    @BeforeEach
    fun init(){
        city = City(description = "New City")
        cities = listOf(City(description = "Hello"), City(description = "World"))
    }

    @Test
    fun `Test create new city`(){
        `when`(repository.save(Mockito.any(City::class.java))).thenReturn(city)

        val savedCity = service.new(city)
        Assertions.assertThat(savedCity).isNotNull
    }

    @Test
    fun `Test get all cities`(){
        `when`(repository.findAll()).thenReturn(cities)

        val response = service.findAll()

        Assertions.assertThat(response).isNotNull
    }

    @Test
    fun `Test get city by id`(){
        `when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(city))

        val savedCity = service.findById(1)
        Assertions.assertThat(savedCity).isNotNull
    }

    @Test
    fun `Test update city by id`(){
        `when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(city))
        `when`(repository.save(Mockito.any(City::class.java))).thenReturn(city)

        val savedCity = service.update(city, 1)
        Assertions.assertThat(savedCity).isNotNull
    }

    @Test
    fun `Test delete city by id`(){
        assertAll({ service.delete(1) })
    }
}