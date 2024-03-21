package com.sena.inventory_manager

import com.fasterxml.jackson.annotation.JsonIgnore
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
class ProductType (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    /*@OneToMany(mappedBy = "city")
    @JsonIgnore
    val locations: List<Location> = mutableListOf()*/
)

interface ProductTypeRepository : JpaRepository<ProductType, Long>

@Service
class ProductTypeService(private val repository: ProductTypeRepository){

    fun findAll(): MutableList<ProductType> = repository.findAll()

    fun findById(id: Long): ProductType = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: ProductType): ProductType {
        body.id = null
        return repository.save(body)
    }

    fun update(body: ProductType, id: Long): ProductType {
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
@RequestMapping("/product_type")
class ProductTypeController(val service: ProductTypeService){

    @GetMapping
    fun all(): MutableList<ProductType> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ProductType = service.findById(id)

    @PostMapping
    fun new(@RequestBody body: ProductType): ProductType = service.new(body)

    @PutMapping("/{id}")
    fun update(@RequestBody body: ProductType, @PathVariable id: Long): ProductType = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}