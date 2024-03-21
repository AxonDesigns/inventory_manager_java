package com.sena.inventory_manager

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime


@Entity
@Table
class LocationType (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "locationType")
    @JsonIgnore
    val locations: List<Location> = mutableListOf()
)

interface LocationTypeRepository : JpaRepository<LocationType, Long>

@Service
class LocationTypeService(private val repository: LocationTypeRepository){

    fun findAll(): MutableList<LocationType> = repository.findAll()

    fun findById(id: Long): LocationType = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: LocationType): LocationType {
        body.id = null
        return repository.save(body)
    }

    fun update(body: LocationType, id: Long): LocationType {
        val entity = findById(id)

        entity.description = body.description
        entity.updatedOn = LocalDateTime.now()

        return repository.save(entity)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }
}

@RestController
@CrossOrigin
@RequestMapping("/location_type")
class LocationTypeController(val service: LocationTypeService){

    @GetMapping
    fun all(): MutableList<LocationType> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): LocationType = if(id > 0L) service.findById(id) else LocationType(description = "")

    @PostMapping
    fun new(@RequestBody body: LocationType): LocationType = service.new(body)


    @PutMapping("/{id}")
    fun update(@RequestBody body: LocationType, @PathVariable id: Long): LocationType = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}