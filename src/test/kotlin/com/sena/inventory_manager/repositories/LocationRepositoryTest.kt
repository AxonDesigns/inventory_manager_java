package com.sena.inventory_manager.repositories

import com.sena.inventory_manager.entities.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.assertj.core.api.Assertions

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class LocationRepositoryTest(
    @Autowired val repository: LocationRepository,
    @Autowired val locationTypeRepository: LocationTypeRepository,
    @Autowired val cityRepository: CityRepository,
    @Autowired val departmentRepository: DepartmentRepository
) {
    private lateinit var locationType: LocationType
    private lateinit var city: City
    private lateinit var department: Department

    @BeforeEach
    fun init(){
        locationType = locationTypeRepository.save(LocationType(description = "New Location Type"))
        city = cityRepository.save(City(description = "New City"))
        department = departmentRepository.save(Department(description = "New Department"))
    }

    @Test
    fun `Test post Location`() {
        val savedLocation = repository.save(Location(
            description = "New Location",
            city = city,
            department = department,
            locationType = locationType
        ))

        Assertions.assertThat(savedLocation).isNotNull
        Assertions.assertThat(savedLocation.id).isGreaterThan(0)
    }

    @Test
    fun `Test get all Location`(){
        val location1 = Location(
            description = "New Location",
            city = city,
            department = department,
            locationType = locationType
        )
        val location2 = Location(
            description = "Another Location",
            city = city,
            department = department,
            locationType = locationType
        )


        repository.save(location1)
        repository.save(location2)
        val locationList = repository.findAll()

        println(locationList)

        Assertions.assertThat(locationList).isNotNull
        Assertions.assertThat(locationList.size).isEqualTo(2)
    }

    @Test
    fun `Test get Location by id`(){
        val location= repository.save(Location(
            description = "New Location",
            city = city,
            department = department,
            locationType = locationType
        ))

        val returnedLocation = repository.findById(location.id!!).get()

        Assertions.assertThat(returnedLocation).isNotNull
    }

    @Test
    fun `Test put Location`(){
        val location = repository.save(Location(
            description = "New Location",
            city = city,
            department = department,
            locationType = locationType
        ))

        val foundLocation = repository.findById(location.id!!).get()
        foundLocation.description = "Location Name"
        foundLocation.city = city
        foundLocation.department = department
        foundLocation.locationType = locationType

        val updatedLocation= repository.save(foundLocation)

        Assertions.assertThat(updatedLocation).isNotNull
        Assertions.assertThat(updatedLocation.id).isEqualTo(location.id)
        Assertions.assertThat(updatedLocation.description).isEqualTo("Location Name")
        Assertions.assertThat(updatedLocation.city).isEqualTo(city)
        Assertions.assertThat(updatedLocation.department).isEqualTo(department)
        Assertions.assertThat(updatedLocation.locationType).isEqualTo(locationType)
    }

    @Test
    fun `Test delete Location by id`(){
        val location = repository.save(Location(
            description = "New Location",
            city = city,
            department = department,
            locationType = locationType
        ))

        repository.deleteById(location.id!!)

        val locationReturn = repository.findById(location.id!!)

        Assertions.assertThat(locationReturn).isEmpty
    }
}