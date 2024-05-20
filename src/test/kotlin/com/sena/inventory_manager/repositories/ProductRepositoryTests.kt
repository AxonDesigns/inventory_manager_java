package com.sena.inventory_manager.repositories

import com.sena.inventory_manager.entities.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductRepositoryTests(
    @Autowired val repository: ProductRepository,
    @Autowired val productTypeRepository: ProductTypeRepository,
    @Autowired val measureUnitRepository: MeasureUnitRepository
) {
    private lateinit var productType: ProductType
    private lateinit var measureUnit: MeasureUnit

    @BeforeEach
    fun init(){
        productType = productTypeRepository.save(ProductType(description = "New Product Type"))
        measureUnit = measureUnitRepository.save(MeasureUnit(description = "New MeasureUnit"))
    }

    @Test
    fun `Test post Product`() {
        val savedProduct = repository.save(Product(
            name = "New Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
        ))

        Assertions.assertThat(savedProduct).isNotNull
        Assertions.assertThat(savedProduct.id).isGreaterThan(0)
    }

    @Test
    fun `Test get all Products`(){
        val product1 = Product(
            name = "New Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
        )
        val product2 = Product(
            name = "Second Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
        )


        repository.save(product1)
        repository.save(product2)
        val productList = repository.findAll()


        Assertions.assertThat(productList).isNotNull
        Assertions.assertThat(productList.size).isEqualTo(2)
    }

    @Test
    fun `Test get Product by id`(){
        val product= repository.save(Product(
            name = "New Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
        ))

        val returnedProduct = repository.findById(product.id!!).get()

        Assertions.assertThat(returnedProduct).isNotNull
    }

    @Test
    fun `Test put Product`(){
        val product = repository.save(Product(
            name = "New Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
        ))

        val foundProduct = repository.findById(product.id!!).get()
        foundProduct.description = "Product Name"
        foundProduct.name = "New Product Name"
        foundProduct.unitPrice = 2L
        foundProduct.unitReference = 2L
        foundProduct.productType = productType
        foundProduct.measureUnit = measureUnit

        val updatedProduct= repository.save(foundProduct)

        Assertions.assertThat(updatedProduct).isNotNull
        Assertions.assertThat(updatedProduct.id).isEqualTo(product.id)
        Assertions.assertThat(updatedProduct.description).isEqualTo("Product Name")
        Assertions.assertThat(updatedProduct.name).isEqualTo("New Product Name")
        Assertions.assertThat(updatedProduct.unitPrice).isEqualTo(2L)
        Assertions.assertThat(updatedProduct.unitReference).isEqualTo(2L)
        Assertions.assertThat(updatedProduct.productType).isEqualTo(productType)
        Assertions.assertThat(updatedProduct.measureUnit).isEqualTo(measureUnit)
    }

    @Test
    fun `Test delete MeasureUnit by id`(){
        val product = repository.save(Product(
            name = "New Product",
            description = "New Product Description",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
        ))

        repository.deleteById(product.id!!)

        val productReturn = repository.findById(product.id!!)

        Assertions.assertThat(productReturn).isEmpty
    }
}