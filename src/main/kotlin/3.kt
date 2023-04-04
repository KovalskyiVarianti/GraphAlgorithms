private data class TspResult(val path: List<Int>, val totalCost: Int)

private fun List<Int>.secondMin() = sorted().getOrNull(1) ?: Int.MAX_VALUE

private fun Graph.tsp(): TspResult {
    val visited = mutableSetOf<Int>().apply { add(0) }
    val currentPath = IntArray(vertices.count() + 1) { -1 }.apply { set(0, 0) }
    var currentWeight = 0
    var currentBound = adjMatrix.sumOf { it.min() + it.secondMin() }.let { bound ->
        if (bound != 1) bound / 2 else bound
    }
    var finalPath = listOf<Int>()
    var finalResult = Int.MAX_VALUE
    fun tspRecursive(level: Int) {
        val previousLevelVertex = currentPath[level - 1]
        val previousLevelDestinations = adjMatrix[previousLevelVertex]
        if (level == vertices.count()) {
            val firstVertexInPath = currentPath.first()
            if (previousLevelDestinations[firstVertexInPath] != 0) {
                val currentResult = currentWeight + previousLevelDestinations[firstVertexInPath]
                if (currentResult < finalResult) {
                    finalPath = currentPath.slice(0 until vertices.count()) + listOf(firstVertexInPath)
                    finalResult = currentResult
                }
            }
            return
        }
        adjMatrix.forEachIndexed { i, destinations ->
            if (previousLevelDestinations[i] != 0 && i !in visited) {
                val temp = currentBound
                currentWeight += previousLevelDestinations[i]
                currentBound -= (previousLevelDestinations.run {
                    if (level == 1) min() else secondMin()
                } + destinations.min()) / 2
                if (currentBound + currentWeight < finalResult) {
                    currentPath[level] = i
                    visited.add(i)
                    tspRecursive(level + 1)
                }
                currentWeight -= previousLevelDestinations[i]
                currentBound = temp
                visited.clear()
                visited.addAll(currentPath.slice(0 until level))
            }
        }
    }
    tspRecursive(1)
    return TspResult(finalPath, finalResult)
}

fun main() {
    val result = listOf(
        listOf(0, 10, 15, 20),
        listOf(10, 0, 35, 25),
        listOf(15, 35, 0, 30),
        listOf(20, 25, 30, 0)
    ).toGraph().tsp()
    println("Minimum cost : ${result.totalCost}")
    println("Path Taken : ")
    println(result.path.joinToString(separator = " -> "))
}

fun List<List<Int>>.toGraph() = Graph(
    mapIndexed { source, row ->
        val edges = mutableListOf<Edge>()
        row.forEachIndexed { destination, weight ->
            if (weight > 0) {
                edges.add(Edge(source, destination, weight))
            }
        }
        Vertex(source, edges)
    },
    this
)

