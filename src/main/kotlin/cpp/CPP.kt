package cpp

import Graph
import Vertex
import readTestGraph

val Graph.isEuler
    get() = vertices.all { it.edges.size % 2 == 0 }

fun Graph.findEulerPath(oriented: Boolean): List<Int> {
    println("Graph is Euler: $isEuler")
    require(isEuler) { "Graph doesn't have an Euler path" }
    val vertices = vertices.mapIndexed { index, vertex ->
        vertex.copy(edges = vertices[index].edges.map { it.copy() }.toMutableList())
    }
    val path = mutableListOf<Int>()
    val startVertex = vertices.first()

    fun findEulerPathRecursive(vertex: Vertex) {
        while (vertex.edges.isNotEmpty()) {
            val edge = vertex.edges.removeFirst()
            val adjacentVertex = vertices[edge.destination]
            if (oriented.not()) adjacentVertex.edges.removeIf { it.destination == vertex.number }
            findEulerPathRecursive(adjacentVertex)
        }
        path.add(vertex.number)
    }

    findEulerPathRecursive(startVertex)
    return path.reversed()
}

fun main() {
    val graph = readTestGraph("src/main/kotlin/cpp/")
    println("Euler path for not oriented graph:")
    println(graph.findEulerPath(oriented = false).map { it + 1 })
    println("Euler path for oriented graph:")
    println(graph.findEulerPath(oriented = true).map { it + 1 })
}