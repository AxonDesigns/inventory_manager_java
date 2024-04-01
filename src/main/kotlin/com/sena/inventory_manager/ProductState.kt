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
class ProductState (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    /*@OneToMany(mappedBy = "city")
    @JsonIgnore
    val locations: List<Location> = mutableListOf()*/
){
    constructor():this(description="")
}

interface ProductStateRepository : JpaRepository<ProductState, Long>

@Service
class ProductStateService(private val repository: ProductStateRepository){

    fun findAll(): MutableList<ProductState> = repository.findAll()

    fun findById(id: Long): ProductState = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: ProductState): ProductState {
        body.id = null
        return repository.save(body)
    }

    fun update(body: ProductState, id: Long): ProductState {
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
@RequestMapping("/product_state")
class ProductStateController(val service: ProductStateService){

    @GetMapping
    fun all(): MutableList<ProductState> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ProductState =  if(id > 0L) service.findById(id) else ProductState()

    @PostMapping
    fun new(@RequestBody body: ProductState): ProductState = service.new(body)

    @PutMapping("/{id}")
    fun update(@RequestBody body: ProductState, @PathVariable id: Long): ProductState = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}