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
class Department (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    val locations: List<Location> = mutableListOf()
){
    constructor() : this(
        description = ""
    )
}

interface DepartmentRepository : JpaRepository<Department, Long>

@Service
class DepartmentService(private val repository: DepartmentRepository){

    fun findAll(): List<Department> = repository.findAll()

    fun findById(id: Long): Department = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: Department): Department {
        body.id = null
        return repository.save(body)
    }

    fun update(body: Department, id: Long): Department {
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
@RequestMapping("/department")
class DepartmentController(val service: DepartmentService){

    @GetMapping
    fun all(): List<Department> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Department = if(id > 0L) service.findById(id) else Department(description = "")

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun new(@RequestBody body: Department): Department = service.new(body)


    @PutMapping("/{id}")
    fun update(@RequestBody body: Department, @PathVariable id: Long): Department = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}