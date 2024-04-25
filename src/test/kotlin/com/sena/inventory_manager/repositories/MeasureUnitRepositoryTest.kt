package com.sena.inventory_manager.repositories

import com.sena.inventory_manager.entities.MeasureUnit
import com.sena.inventory_manager.entities.MeasureUnitRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MeasureUnitRepositoryTest(
    @Autowired val repository: MeasureUnitRepository
) {

    @Test
    fun `Test post MeasureUnit`() {
        val savedMeasureUnit = repository.save(MeasureUnit(description = "New MeasureUnit"))

        Assertions.assertThat(savedMeasureUnit).isNotNull
        Assertions.assertThat(savedMeasureUnit.id).isGreaterThan(0)
    }


    @Test
    fun `Test get all MeasureUnit`(){
        val measureUnit1 = MeasureUnit(description = "A MeasureUnit")
        val measureUnit2 = MeasureUnit(description = "Yet Another MeasureUnit")


        repository.save(measureUnit1)
        repository.save(measureUnit2)
        val measureUnitList = repository.findAll()

        println(measureUnitList)

        Assertions.assertThat(measureUnitList).isNotNull
        Assertions.assertThat(measureUnitList.size).isEqualTo(2)
    }

    @Test
    fun `Test get MeasureUnit by id`(){
        val measureUnit= repository.save(MeasureUnit(description = "New MeasureUnit"))

        val returnedMeasureUnit = repository.findById(measureUnit.id!!).get()

        Assertions.assertThat(returnedMeasureUnit).isNotNull
    }

    @Test
    fun `Test put MeasureUnit`(){
        val measureUnit = repository.save(MeasureUnit(description = "New MeasureUnit"))

        val foundMeasureUnit = repository.findById(measureUnit.id!!).get()
        foundMeasureUnit.description = "MeasureUnit Name"

        val updatedMeasureUnit= repository.save(foundMeasureUnit)

        Assertions.assertThat(updatedMeasureUnit).isNotNull
        Assertions.assertThat(updatedMeasureUnit.id).isEqualTo(measureUnit.id)
        Assertions.assertThat(updatedMeasureUnit.description).isEqualTo("MeasureUnit Name")
    }

    @Test
    fun `Test delete MeasureUnit by id`(){
        val measureUnit = repository.save(MeasureUnit(description = "New MeasureUnit"))

        repository.deleteById(measureUnit.id!!)

        val measureUnitReturn = repository.findById(measureUnit.id!!)

        Assertions.assertThat(measureUnitReturn).isEmpty
    }
}