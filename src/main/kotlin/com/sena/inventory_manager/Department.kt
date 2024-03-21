package com.sena.inventory_manager

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
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

@Service
class DepartmentService(private val repository: DepartmentRepository){

    fun findAll(): MutableList<Department> = repository.findAll()

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
@RequestMapping("/department")
class DepartmentController(val service: DepartmentService){

    @GetMapping
    fun all(): MutableList<Department> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Department = service.findById(id)

    @PostMapping
    fun new(@RequestBody body: Department): Department = service.new(body)


    @PutMapping("/{id}")
    fun update(@RequestBody body: Department, @PathVariable id: Long): Department = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}