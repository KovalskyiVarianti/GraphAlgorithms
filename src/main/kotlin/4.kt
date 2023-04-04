import java.util.*

private fun bfs(adjMatrix: List<List<Int>>, source: Int, sink: Int, parent: MutableList<Int>): Boolean {
    val visited = mutableSetOf<Int>()
    val queue = LinkedList<Int>()
    queue.add(source)
    visited.add(source)
    parent[source] = -1

    while (queue.isNotEmpty()) {
        val vertexNumber = queue.poll()
        adjMatrix[vertexNumber].forEachIndexed { destinationNumber, weight ->
            if (destinationNumber !in visited && weight > 0) {
                if (destinationNumber == sink) {
                    parent[destinationNumber] = vertexNumber
                    return true
                }
                queue.add(destinationNumber)
                parent[destinationNumber] = vertexNumber
                visited.add(destinationNumber)
            }
        }
    }
    return false
}

fun fordFulkerson(graph: Graph, source: Int, sink: Int): Int {
    var vertexNumber: Int
    var destinationNumber: Int

    val adjMatrix = graph.adjMatrix.toMutableList().map { it.toMutableList() }

    val parent = MutableList(adjMatrix.count()) { 0 }
    var maxFlow = 0
    while (bfs(adjMatrix, source, sink, parent)) {
        var pathFlow = Int.MAX_VALUE
        destinationNumber = sink
        while (destinationNumber != source) {
            vertexNumber = parent[destinationNumber]
            pathFlow = pathFlow.coerceAtMost(adjMatrix[vertexNumber][destinationNumber])
            destinationNumber = parent[destinationNumber]
        }
        destinationNumber = sink
        while (destinationNumber != source) {
            vertexNumber = parent[destinationNumber]
            adjMatrix[vertexNumber][destinationNumber] -= pathFlow
            adjMatrix[destinationNumber][vertexNumber] += pathFlow
            destinationNumber = parent[destinationNumber]
        }
        maxFlow += pathFlow
    }

    return maxFlow
}

fun main() {
    val graph = listOf(
        listOf(0, 16, 13, 0, 0, 0),
        listOf(0, 0, 10, 12, 0, 0),
        listOf(0, 4, 0, 0, 14, 0),
        listOf(0, 0, 9, 0, 0, 20),
        listOf(0, 0, 0, 7, 0, 4),
        listOf(0, 0, 0, 0, 0, 0)
    ).toGraph()

    println("The maximum possible flow is " + fordFulkerson(graph, 0, 5))
}