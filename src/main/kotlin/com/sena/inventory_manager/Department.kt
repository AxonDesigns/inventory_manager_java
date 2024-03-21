package com.sena.inventory_manager

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
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
class Department (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    val locations: List<Location> = mutableListOf()
)

interface DepartmentRepository : JpaRepository<Department, Long>

@RestController
@RequestMapping("/department")
class DepartmentController(val repository: DepartmentRepository){

    @GetMapping
    fun all(): MutableList<Department> = repository.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Department = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    fun new(@RequestBody body: Department): Department {
        body.id = null
        return repository.save(body)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody body: Department, @PathVariable id: Long): Department {
        val entity = repository.findById(id).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        entity.description = body.description
        entity.updatedOn = LocalDateTime.now()

        return repository.save(entity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        repository.deleteById(id)
    }

}