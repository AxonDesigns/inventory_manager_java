package com.sena.inventory_manager.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime


@Entity
@Table
class MeasureUnit (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "measureUnit")
    @JsonIgnore
    val products: List<Product> = mutableListOf()
){
    constructor():this(description="")
}

interface MeasureUnitRepository : JpaRepository<MeasureUnit, Long>

@Service
class MeasureUnitService(private val repository: MeasureUnitRepository){

    fun findAll(): List<MeasureUnit> = repository.findAll()

    fun findById(id: Long): MeasureUnit = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: MeasureUnit): MeasureUnit {
        body.id = null
        return repository.save(body)
    }

    fun update(body: MeasureUnit, id: Long): MeasureUnit {
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
@RequestMapping("/measure_unit")
class MeasureUnitController(val service: MeasureUnitService){

    @GetMapping
    fun all(): List<MeasureUnit> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): MeasureUnit = if(id > 0L) service.findById(id) else MeasureUnit()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun new(@RequestBody body: MeasureUnit): MeasureUnit = service.new(body)

    @PutMapping("/{id}")
    fun update(@RequestBody body: MeasureUnit, @PathVariable id: Long): MeasureUnit = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}