package com.sena.inventory_manager.entities

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InventoryManagerApplication

fun main(args: Array<String>) {
	runApplication<InventoryManagerApplication>(*args)
}
