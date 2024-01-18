package nl.vintik.sample

import ProductsService
import nl.vintik.sample.model.Product

class ProductsController(
    private val productsService: ProductsService,
) {
    fun execute(): List<Product> =
        productsService.findAllProducts()

}