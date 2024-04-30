package com.sena.inventory_manager.services

import com.sena.inventory_manager.entities.*
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
class ProductStateServiceTests {
    @Mock
    private lateinit var repository: ProductStateRepository
    @InjectMocks
    private lateinit var service: ProductStateService

    private lateinit var productState: ProductState
    private lateinit var productStates: List<ProductState>

    @BeforeEach
    fun init(){
        productState = ProductState(description = "New ProductState")
        productStates = listOf(ProductState(description = "Hello"), ProductState(description = "World"))
    }

    @Test
    fun `Test create new ProductState`(){
        Mockito.`when`(repository.save(Mockito.any(ProductState::class.java))).thenReturn(productState)

        val savedProductState = service.new(productState)
        Assertions.assertThat(savedProductState).isNotNull
    }

    @Test
    fun `Test get all ProductState`(){
        Mockito.`when`(repository.findAll()).thenReturn(productStates)

        val response = service.findAll()

        Assertions.assertThat(response).isNotNull
    }

    @Test
    fun `Test get ProductState by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(productState))

        val savedProductState = service.findById(1)
        Assertions.assertThat(savedProductState).isNotNull
    }

    @Test
    fun `Test update ProductState by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(productState))
        Mockito.`when`(repository.save(Mockito.any(ProductState::class.java))).thenReturn(productState)

        val savedProductState = service.update(productState, 1)
        Assertions.assertThat(savedProductState).isNotNull
    }

    @Test
    fun `Test delete ProductState by id`(){
        assertAll({ service.delete(1) })
    }

}