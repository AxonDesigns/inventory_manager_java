package com.sena.inventory_manager

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/api")
class ApiController {

    @GetMapping
    fun tables() = listOf("city", "department", "location", "location_type", "measure_unit", "product", "product_state", "product_type")
}