package com.sena.inventory_manager.repositories

import com.sena.inventory_manager.entities.City
import com.sena.inventory_manager.entities.CityRepository
import com.sena.inventory_manager.entities.LocationType
import com.sena.inventory_manager.entities.LocationTypeRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

class LocationTypeRepositoryTests {

    @DataJpaTest
    @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
    class LocationTypeRepositoryTests(
        @Autowired val repository: LocationTypeRepository
    ) {

        @Test
        fun `Test post LocationType`() {
            val savedLocationType = repository.save(LocationType(description = "New LocationType"))

            Assertions.assertThat(savedLocationType).isNotNull
            Assertions.assertThat(savedLocationType.id).isGreaterThan(0)
        }

        @Test
        fun `Test get all LocationType`(){
            val locationType1 = LocationType(description = "A LocationType")
            val locationType2 = LocationType(description = "Yet Another LocationType")

            repository.save(locationType1)
            repository.save(locationType2)
            val LocationTypeList = repository.findAll()

            println(LocationTypeList)

            Assertions.assertThat(LocationTypeList).isNotNull
            Assertions.assertThat(LocationTypeList.size).isEqualTo(2)
        }

        @Test
        fun `Test get LocationType by id`(){
            val LocationType = repository.save(LocationType(description = "New LocationType"))

            val returnedLocationType = repository.findById(LocationType.id!!).get()

            Assertions.assertThat(returnedLocationType).isNotNull
        }

        @Test
        fun `Test put LocationType`(){
            val LocationType = repository.save(LocationType(description = "New LocationType"))

            val foundLocationType = repository.findById(LocationType.id!!).get()
            foundLocationType.description = "LocationType Name"

            val updatedLocationType = repository.save(foundLocationType)

            Assertions.assertThat(updatedLocationType).isNotNull
            Assertions.assertThat(updatedLocationType.id).isEqualTo(LocationType.id)
            Assertions.assertThat(updatedLocationType.description).isEqualTo("LocationType Name")
        }

        @Test
        fun `Test delete LocationType by id`(){
            val LocationType = repository.save(LocationType(description = "New LocationType"))

            repository.deleteById(LocationType.id!!)

            val LocationTypeReturn = repository.findById(LocationType.id!!)

            Assertions.assertThat(LocationTypeReturn).isEmpty
        }
    }

}