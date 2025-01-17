package com.sena.inventory_manager.entities

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
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
){
    constructor() : this(
        description = "",
        city = City(),
        department = Department(),
        locationType = LocationType()
    )
}

class LocationRequest(
    var description: String?,
    var city: Long?,
    var department: Long?,
    var locationType: Long?,
)

interface LocationRepository : JpaRepository<Location, Long>

@Service
class LocationService(
    private val repository: LocationRepository,
    val cityRepository: CityRepository,
    val departmentRepository: DepartmentRepository,
    val locationTypeRepository: LocationTypeRepository
){

    fun findAll(): List<Location> = repository.findAll()

    fun findById(id: Long): Location = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: LocationRequest): Location {
        val city = cityRepository.findById(body.city!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val department = departmentRepository.findById(body.department!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val locationType = locationTypeRepository.findById(body.locationType!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        if (body.description == null || body.department == null || body.locationType == null) throw ResponseStatusException(HttpStatus.BAD_REQUEST)

        return repository.save(Location(null, body.description!!, city!!, department!!, locationType!!))
    }

    fun update(body: LocationRequest, id: Long): Location {
        val entity = repository.findById(id).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val city = if(body.city != null) cityRepository.findById(body.city!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else null

        val department = if(body.department != null) departmentRepository.findById(body.department!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else null

        val locationType = if(body.locationType != null) locationTypeRepository.findById(body.locationType!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else null

        body.description?.let { entity.description = it }
        city?.let { entity.city = it }
        department?.let { entity.department = it }
        locationType?.let { entity.locationType = it }

        entity.updatedOn = LocalDateTime.now()

        return repository.save(entity)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }
}

@RestController
@CrossOrigin
@RequestMapping("/location")
class LocationController(val service: LocationService){

    @GetMapping
    fun all(): List<Location> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Location = if(id > 0L) service.findById(id) else
        Location()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun new(@RequestBody body: LocationRequest): Location = service.new(body)

    @PutMapping("/{id}")
    fun update(@RequestBody body: LocationRequest, @PathVariable id: Long): Location = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}