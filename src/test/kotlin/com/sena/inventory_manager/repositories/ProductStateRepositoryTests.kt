package com.sena.inventory_manager.repositories

import com.sena.inventory_manager.entities.City
import com.sena.inventory_manager.entities.ProductState
import com.sena.inventory_manager.entities.ProductStateRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductStateRepositoryTests(
    @Autowired val repository: ProductStateRepository
) {

    @Test
    fun `Test post productState`() {
        val savedProductState = repository.save(ProductState(description = "New ProductState"))

        Assertions.assertThat(savedProductState).isNotNull
        Assertions.assertThat(savedProductState.id).isGreaterThan(0)
    }

    @Test
    fun `Test get all cities`(){
        val productState1 = ProductState(description = "A ProductState")
        val productState2 = ProductState(description = "Yet Another ProductState")

        repository.save(productState1)
        repository.save(productState2)
        val productStateList = repository.findAll()

        println(productStateList)

        Assertions.assertThat(productStateList).isNotNull
        Assertions.assertThat(productStateList.size).isEqualTo(2)
    }

    @Test
    fun `Test get productState by id`(){
        val productState = repository.save(ProductState(description = "New ProductState"))

        val returnedProductState = repository.findById(productState.id!!).get()

        Assertions.assertThat(returnedProductState).isNotNull
    }

    @Test
    fun `Test put productState`(){
        val productState = repository.save(ProductState(description = "New ProductState"))

        val foundProductState = repository.findById(productState.id!!).get()
        foundProductState.description = "ProductState Name"

        val updatedProductState = repository.save(foundProductState)

        Assertions.assertThat(updatedProductState).isNotNull
        Assertions.assertThat(updatedProductState.id).isEqualTo(productState.id)
        Assertions.assertThat(updatedProductState.description).isEqualTo("ProductState Name")
    }

    @Test
    fun `Test delete productState by id`(){
        val productState = repository.save(ProductState(description = "New ProductState"))

        repository.deleteById(productState.id!!)

        val producStateReturn = repository.findById(productState.id!!)

        Assertions.assertThat(producStateReturn).isEmpty
    }
}