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
class LocationTypeServiceTests{
    @Mock
    private lateinit var repository: LocationTypeRepository
    @InjectMocks
    private lateinit var service: LocationTypeService

    private lateinit var locationType: LocationType
    private lateinit var locationTypes: List<LocationType>

    @BeforeEach
    fun init(){
        locationType = LocationType(description = "New LocationType")
        locationTypes = listOf(LocationType(description = "Hello"), LocationType(description = "World"))
    }

    @Test
    fun `Test create new LocationType`(){
        Mockito.`when`(repository.save(Mockito.any(LocationType::class.java))).thenReturn(locationType)

        val savedLocationType = service.new(locationType)
        Assertions.assertThat(savedLocationType).isNotNull
    }

    @Test
    fun `Test get all cities`(){
        Mockito.`when`(repository.findAll()).thenReturn(locationTypes)

        val response = service.findAll()

        Assertions.assertThat(response).isNotNull
    }

    @Test
    fun `Test get Department by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(locationType))

        val savedLocationType = service.findById(1)
        Assertions.assertThat(savedLocationType).isNotNull
    }

    @Test
    fun `Test update Department by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(locationType))
        Mockito.`when`(repository.save(Mockito.any(LocationType::class.java))).thenReturn(locationType)

        val savedDepartment = service.update(locationType, 1)
        Assertions.assertThat(savedDepartment).isNotNull
    }

    @Test
    fun `Test delete LocationType by id`(){
        assertAll({ service.delete(1) })
    }
}