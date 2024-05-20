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
class ProductServiceTests {

    @Mock
    private lateinit var repository: ProductRepository
    @Mock
    private lateinit var productTypeRepository: ProductTypeRepository
    @Mock
    private lateinit var measureUnitRepository: MeasureUnitRepository
    @InjectMocks
    private lateinit var service: ProductService

    private lateinit var product: Product
    private lateinit var products: List<Product>
    private lateinit var productType: ProductType
    private lateinit var measureUnit: MeasureUnit
    private lateinit var productRequest: ProductRequest

    @BeforeEach
    fun init(){
        productRequest = ProductRequest(
            name = "New Product Name",
            description = "New Product",
            productTypeId = 1L,
            measureUnitId = 1L,
            unitPrice = 1L,
            unitReference = 1L
        )
        productType = ProductType(description = "New Product Type")
        measureUnit = MeasureUnit(description = "New MeasureUnit")
        product = Product(
            name = "New Product Name",
            description = "New Product",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
        )
        products = listOf(
            Product(
            name = "New Product Name",
            description = "New Product",
            productType = productType,
            measureUnit = measureUnit,
            unitPrice = 1L,
            unitReference = 1L
            ),
            Product(
                name = "New Product Name",
                description = "New Product",
                productType = productType,
                measureUnit = measureUnit,
                unitPrice = 1L,
                unitReference = 1L
            )
        )
    }

    @Test
    fun `Test create new city`(){
        Mockito.`when`(repository.save(Mockito.any(Product::class.java))).thenReturn(product)
        Mockito.`when`(productTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(productType))
        Mockito.`when`(measureUnitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(measureUnit))

        val savedProduct = service.new(productRequest)
        Assertions.assertThat(savedProduct).isNotNull
    }

    @Test
    fun `Test get all products`(){
        Mockito.`when`(repository.findAll()).thenReturn(products)

        val response = service.findAll()

        Assertions.assertThat(response).isNotNull
    }

    @Test
    fun `Test get product by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(product))

        val savedProduct = service.findById(1)
        Assertions.assertThat(savedProduct).isNotNull
    }

    @Test
    fun `Test update product by id`(){
        Mockito.`when`(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(product))
        Mockito.`when`(productTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(productType))
        Mockito.`when`(measureUnitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(measureUnit))
        Mockito.`when`(repository.save(Mockito.any(Product::class.java))).thenReturn(product)

        val savedProduct = service.update(productRequest, 1)
        Assertions.assertThat(savedProduct).isNotNull
    }

    @Test
    fun `Test delete product by id`(){
        assertAll({ service.delete(1) })
    }

}