import nl.vintik.sample.model.Product
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest
import java.util.concurrent.Executors
import java.util.Collections

class ProductsService(private val productTable: DynamoDbAsyncTable<Product>) {
    fun findAllProducts(): List<Product> {
        val products = Collections.synchronizedList(mutableListOf<Product>())
        val executor = Executors.newVirtualThreadPerTaskExecutor()

        try {
            val futures = (0 until parallelScanTotalSegments).map { segment ->
                executor.submit {
                    val scanRequest = ScanEnhancedRequest.builder()
                        .segment(segment)
                        .totalSegments(parallelScanTotalSegments)
                        .limit(parallelScanPageSize)
                        .build()

                    productTable.scan(scanRequest).items().subscribe {
                        products.add(it)
                    }.get()
                }
            }

            futures.forEach { it.get() } // Wait for all virtual threads to complete
        } finally {
            executor.shutdown() // Ensure the executor is properly shut down
        }

        return products
    }

    companion object {
        private const val parallelScanTotalSegments = 5
        private const val parallelScanPageSize = 25
    }
}
