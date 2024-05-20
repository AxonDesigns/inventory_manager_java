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
class ProductType (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var description: String,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "productType")
    @JsonIgnore
    val products: List<Product> = mutableListOf()
){
    constructor():this(description="")
}

interface ProductTypeRepository : JpaRepository<ProductType, Long>

@Service
class ProductTypeService(private val repository: ProductTypeRepository){

    fun findAll(): List<ProductType> = repository.findAll()

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
@CrossOrigin
@RequestMapping("/product_type")
class ProductTypeController(val service: ProductTypeService){

    @GetMapping
    fun all(): List<ProductType> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ProductType =  if(id > 0L) service.findById(id) else ProductType(description = "")

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun new(@RequestBody body: ProductType): ProductType = service.new(body)

    @PutMapping("/{id}")
    fun update(@RequestBody body: ProductType, @PathVariable id: Long): ProductType = service.update(body, id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}