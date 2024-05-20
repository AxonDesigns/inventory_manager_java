package com.sena.inventory_manager.services

import com.sena.inventory_manager.entities.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.assertAll
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

@ExtendWith(MockitoExtension::class)
class LocationServiceTests {

    @Mock
    private lateinit var repository: LocationRepository
    @Mock
    private lateinit var cityRepository: CityRepository
    @Mock
    private lateinit var departmentRepository: DepartmentRepository
    @Mock
    private lateinit var locationTypeRepository: LocationTypeRepository

    @InjectMocks
    private lateinit var service: LocationService

    private lateinit var location: Location
    private lateinit var locations: List<Location>
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationType: LocationType
    private lateinit var city: City
    private lateinit var department: Department

    @BeforeEach
    fun init(){
        locationRequest = LocationRequest(
            description = "New Location",
            city = 1L,
            department = 1L,
            locationType = 1L
        )
        locationType = LocationType(description = "New Location Type")
        city = City(description = "New City")
        department = Department(description = "New Department")
        location = Location(
            description = "New Location",
            city = city,
            department = department,
            locationType = locationType
        )
        locations = listOf(
            Location(
                description = "New Location",
                city = city,
                department = department,
                locationType = locationType
            ),
            Location(
                description = "New Location",
                city = city,
                department = department,
                locationType = locationType
            )
        )
    }

    @Test
    fun `Test create new location`(){
        Mockito.`when`(repository.save(Mockito.any(Location::class.java))).thenReturn(location)
        Mockito.`when`(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(city))
        Mockito.`when`(departmentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(department))
        Mockito.`when`(locationTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(locationType))

        val savedLocation = service.new(locationRequest)
        Assertions.assertThat(savedLocation).isNotNull
    }

    @Test
    fun `Test get all locations`(){
        Mockito.`when`(repository.findAll()).thenReturn(locations)

        val response = service.findAll()

        Assertions.assertThat(response).isNotNull
    }

    @Test
    fun `Test get location by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(location))

        val savedLocation = service.findById(1)
        Assertions.assertThat(savedLocation).isNotNull
    }

    @Test
    fun `Test update location by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(location))
        Mockito.`when`(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(city))
        Mockito.`when`(departmentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(department))
        Mockito.`when`(locationTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(locationType))
        Mockito.`when`(repository.save(Mockito.any(Location::class.java))).thenReturn(location)

        val savedLocation = service.update(locationRequest, 1)
        Assertions.assertThat(savedLocation).isNotNull
    }

    @Test
    fun `Test delete location by id`() {
        assertAll({ service.delete(1) })
    }

}