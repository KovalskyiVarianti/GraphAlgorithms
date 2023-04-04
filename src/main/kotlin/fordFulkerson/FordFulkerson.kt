package fordFulkerson

import Graph
import readTestGraph
import java.util.*

fun fordFulkerson(graph: Graph, source: Int, sink: Int): Int {
    val adjMatrix = graph.adjMatrix.toMutableList().map { it.toMutableList() }
    val parent = MutableList(adjMatrix.count()) { 0 }
    var vertexNumber: Int
    var destinationNumber: Int
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

fun main() {
    val graph = readTestGraph("src/main/kotlin/fordFulkerson/")
    val source = graph.vertices.first().number
    val sink = graph.vertices.last().number
    println("The maximum possible flow from $source to $sink: " + fordFulkerson(graph, source, sink))
}