package com.sena.inventory_manager.services

import com.sena.inventory_manager.entities.ProductType
import com.sena.inventory_manager.entities.ProductTypeRepository
import com.sena.inventory_manager.entities.ProductTypeService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
@ExtendWith(MockitoExtension::class)
class ProductTypeServiceTests {
    @Mock
    private lateinit var repository: ProductTypeRepository
    @InjectMocks
    private lateinit var service: ProductTypeService

    private lateinit var productType: ProductType
    private lateinit var productTypes: List<ProductType>

    @BeforeEach
    fun init(){
        productType = ProductType(description = "New ProductType")
        productTypes = listOf(ProductType(description = "Hello"), ProductType(description = "World"))
    }

    @Test
    fun `Test create new ProductType`(){
        Mockito.`when`(repository.save(Mockito.any(ProductType::class.java))).thenReturn(productType)

        val savedProductType = service.new(productType)
        Assertions.assertThat(savedProductType).isNotNull
    }

    @Test
    fun `Test get all ProductType`(){
        Mockito.`when`(repository.findAll()).thenReturn(productTypes)

        val response = service.findAll()

        Assertions.assertThat(response).isNotNull
    }

    @Test
    fun `Test get ProductType by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(productType))

        val savedProductType = service.findById(1)
        Assertions.assertThat(savedProductType).isNotNull
    }

    @Test
    fun `Test update ProductType by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(productType))
        Mockito.`when`(repository.save(Mockito.any(ProductType::class.java))).thenReturn(productType)

        val savedProductType = service.update(productType, 1)
        Assertions.assertThat(savedProductType).isNotNull
    }

    @Test
    fun `Test delete ProductType by id`(){
        assertAll({ service.delete(1) })
    }
}