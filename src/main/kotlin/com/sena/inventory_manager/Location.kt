package com.sena.inventory_manager

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime


@Entity
@Table
class Location (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    @ManyToOne
    var city: City,
    @ManyToOne
    var department: Department,
    @ManyToOne
    var locationType: LocationType,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now()
)

class LocationRequest(
    var id: Long? = null,
    var description: String,
    var cityId: Long,
    var departmentId: Long,
    var locationTypeId: Long,
    var updatedOn: LocalDateTime = LocalDateTime.now()
)

interface LocationRepository : JpaRepository<Location, Long>

@Service
class LocationService(
    private val repository: LocationRepository,
    val cityRepository: CityRepository,
    val departmentRepository: DepartmentRepository,
    val locationTypeRepository: LocationTypeRepository
){

    fun findAll(): MutableList<Location> = repository.findAll()

    fun findById(id: Long): Location = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: LocationRequest): Location {
        val city = cityRepository.findById(body.cityId).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val department = departmentRepository.findById(body.departmentId).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val locationType = locationTypeRepository.findById(body.locationTypeId).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        return repository.save(Location(null, body.description, city, department, locationType))
    }

    fun update(body: LocationRequest, id: Long): Location {
        val entity = repository.findById(id).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val city = cityRepository.findById(body.cityId).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val department = departmentRepository.findById(body.departmentId).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val locationType = locationTypeRepository.findById(body.locationTypeId).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        entity.description = body.description
        entity.city = city
        entity.department = department
        entity.locationType = locationType
        entity.updatedOn = LocalDateTime.now()

        return repository.save(entity)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }
}

@RestController
@RequestMapping("/location")
class LocationController(val service: LocationService){

    @GetMapping
    fun all(): MutableList<Location> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Location = service.findById(id)

    @PostMapping
    fun new(@RequestBody body: LocationRequest): Location = service.new(body)

    @PutMapping("/{id}")
    fun update(@RequestBody body: LocationRequest, @PathVariable id: Long): Location = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}