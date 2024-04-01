package com.sena.inventory_manager

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
class Product (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
    var description: String,
    var expiresOn: LocalDateTime? = null,
    @ManyToOne
    var productType: ProductType,
    @ManyToOne
    var measureUnit: MeasureUnit,
    var unitPrice: Long,
    var unitReference: Long,
    var createdOn: LocalDateTime = LocalDateTime.now(),
    var updatedOn: LocalDateTime = LocalDateTime.now(),

    /*@OneToMany(mappedBy = "city")
    @JsonIgnore
    val locations: List<Location> = mutableListOf()*/
) {
    constructor() : this(
        description = "",
        name = "",
        productType = ProductType(description = ""),
        measureUnit = MeasureUnit(description = ""),
        unitPrice = 1L,
        unitReference = 1L
    )
}

class ProductRequest (
    var name: String?,
    var description: String?,
    var expiresOn: LocalDateTime? = null,
    var productTypeId: Long?,
    var measureUnitId: Long?,
    var unitPrice: Long?,
    var unitReference: Long?,
)

interface ProductRepository : JpaRepository<Product, Long>

@Service
class ProductService(
    private val repository: ProductRepository,
    val productTypeRepository: ProductTypeRepository,
    val measureUnitRepository: MeasureUnitRepository
){

    fun findAll(): MutableList<Product> = repository.findAll()

    fun findById(id: Long): Product = repository.findById(id).orElseThrow{
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun new(body: ProductRequest): Product {

        val productType = if(body.productTypeId != null) productTypeRepository.findById(body.productTypeId!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else null


        val measureUnit = if(body.measureUnitId != null) measureUnitRepository.findById(body.measureUnitId!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else null

        return repository.save(Product(null, body.name!!, body.description!!, body.expiresOn, productType!!, measureUnit!!, body.unitPrice!!, body.unitReference!!))
    }

    fun update(body: ProductRequest, id: Long): Product {
        val entity = findById(id)

        val productType = if(body.productTypeId != null) productTypeRepository.findById(body.productTypeId!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else null
        val measureUnit = if(body.measureUnitId != null) measureUnitRepository.findById(body.measureUnitId!!).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else null

        body.name?.let { entity.name = it }
        body.description?.let { entity.description = it }
        productType?.let { entity.productType = it }
        measureUnit?.let { entity.measureUnit = it }
        body.unitPrice?.let{entity.unitPrice = it}
        body.unitReference?.let{entity.unitReference = it}

        entity.expiresOn = body.expiresOn
        entity.updatedOn = LocalDateTime.now()

        return repository.save(entity)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }
}

@RestController
@CrossOrigin
@RequestMapping("/product")
class ProductController(val service: ProductService){

    @GetMapping
    fun all(): MutableList<Product> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Product = if(id > 0L) service.findById(id) else Product()

    @PostMapping
    fun new(@RequestBody body: ProductRequest): Product = service.new(body)

    @PutMapping("/{id}")
    fun update(@RequestBody body: ProductRequest, @PathVariable id: Long): Product = service.update(body, id)


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)
}