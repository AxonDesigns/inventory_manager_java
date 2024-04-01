package com.sena.inventory_manager

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
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
class City (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    val locations: List<Location> = mutableListOf()
){
    constructor() : this(
        description = ""
    )
}

interface CityRepository : JpaRepository<City, Long>

@Service
class CityService(private val repository: CityRepository){

    fun findAll(): MutableList<City> = repository.findAll()

    fun findById(id: Long): City = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: City): City {
        body.id = null
        return repository.save(body)
    }

    fun update(body: City, id: Long): City {
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
@RequestMapping("/city")
class CityController(val service: CityService){

    @GetMapping
    fun all(): MutableList<City> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): City = if(id > 0L) service.findById(id) else City()

    @PostMapping
    fun new(@RequestBody body: City): City = service.new(body)

    @PutMapping("/{id}")
    fun update(@RequestBody body: City, @PathVariable id: Long): City = service.update(body, id)


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)
}