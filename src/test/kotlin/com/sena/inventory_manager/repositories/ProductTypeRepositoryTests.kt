package com.sena.inventory_manager.repositories

import com.sena.inventory_manager.entities.ProductType
import com.sena.inventory_manager.entities.ProductTypeRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductTypeRepositoryTests(
    @Autowired val repository: ProductTypeRepository
) {

    @Test
    fun `Test post productType`() {
        val savedProductType = repository.save(ProductType(description = "New ProductType"))

        Assertions.assertThat(savedProductType).isNotNull
        Assertions.assertThat(savedProductType.id).isGreaterThan(0)
    }

    @Test
    fun `Test get all cities`(){
        val productType1 = ProductType(description = "A City")
        val productType2 = ProductType(description = "Yet Another City")

        repository.save(productType1)
        repository.save(productType2)
        val productTypeList = repository.findAll()

        println(productTypeList)

        Assertions.assertThat(productTypeList).isNotNull
        Assertions.assertThat(productTypeList.size).isEqualTo(2)
    }

    @Test
    fun `Test get productType by id`(){
        val productType = repository.save(ProductType(description = "New ProductState"))

        val returnedProductType = repository.findById(productType.id!!).get()

        Assertions.assertThat(returnedProductType).isNotNull
    }

    @Test
    fun `Test put productType`(){
        val productType = repository.save(ProductType(description = "New ProductState"))

        val foundProductType = repository.findById(productType.id!!).get()
        foundProductType.description = "ProductType Name"

        val updatedProductType = repository.save(foundProductType)

        Assertions.assertThat(updatedProductType).isNotNull
        Assertions.assertThat(updatedProductType.id).isEqualTo(productType.id)
        Assertions.assertThat(updatedProductType.description).isEqualTo("ProductType Name")
    }

    @Test
    fun `Test delete productType by id`(){
        val productType = repository.save(ProductType(description = "New ProductType"))

        repository.deleteById(productType.id!!)

        val productStateReturn = repository.findById(productType.id!!)

        Assertions.assertThat(productStateReturn).isEmpty
    }
}