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
class MeasureUnitServiceTests {
    @Mock
    private lateinit var repository: MeasureUnitRepository
    @InjectMocks
    private lateinit var service: MeasureUnitService

    private lateinit var measureUnit: MeasureUnit
    private lateinit var measureUnits: List<MeasureUnit>

    @BeforeEach
    fun init(){
        measureUnit = MeasureUnit(description = "New MeasureUnit")
        measureUnits = listOf(MeasureUnit(description = "Hello"), MeasureUnit(description = "World"))
    }

    @Test
    fun `Test create new city`(){
        Mockito.`when`(repository.save(Mockito.any(MeasureUnit::class.java))).thenReturn(measureUnit)

        val savedMeasureUnit = service.new(measureUnit)
        Assertions.assertThat(savedMeasureUnit).isNotNull
    }

    @Test
    fun `Test get all MeasureUnit`(){
        Mockito.`when`(repository.findAll()).thenReturn(measureUnits)

        val response = service.findAll()

        Assertions.assertThat(response).isNotNull
    }

    @Test
    fun `Test get MeasureUnit by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(measureUnit))

        val savedMeasureUnit = service.findById(1)
        Assertions.assertThat(savedMeasureUnit).isNotNull
    }

    @Test
    fun `Test update MeasureUnit by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(measureUnit))
        Mockito.`when`(repository.save(Mockito.any(MeasureUnit::class.java))).thenReturn(measureUnit)

        val savedMeasureUnit = service.update(measureUnit, 1)
        Assertions.assertThat(savedMeasureUnit).isNotNull
    }

    @Test
    fun `Test delete MeasureUnit by id`(){
        assertAll({ service.delete(1) })
    }
}