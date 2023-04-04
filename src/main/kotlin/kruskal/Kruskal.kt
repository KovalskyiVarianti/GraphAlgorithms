package kruskal

import Edge
import Graph
import readTestGraph

private fun Graph.kruskal(): List<Edge> {
    val edgesByWeight = vertices.flatMap { it.edges }.sortedBy { it.weight }
    val result = mutableListOf<Edge>()
    val parent = IntArray(vertices.count()) { it }
    edgesByWeight.forEach { edge ->
        val firstVertex = getVertex(edge.source, parent)
        val secondVertex = getVertex(edge.destination, parent)
        if (firstVertex != secondVertex) {
            result.add(edge)
            parent[firstVertex] = secondVertex
        }
    }
    return result
}

private fun getVertex(vertex: Int, parent: IntArray): Int {
    var temp = vertex
    while (temp != parent[temp]) {
        parent[temp] = parent[parent[temp]]
        temp = parent[temp]
    }
    return temp
}

fun main() {
    val graph = readTestGraph("src/cpp.main/kotlin/kruskal/")
    val result = graph.kruskal()
    println("Min spanning tree (Kruskal algorithm)")
    result.forEach { edge ->
        println("[${edge.source + 1}--(${edge.weight})-->${edge.destination + 1}]")
    }
    println("Total weight: ${result.sumOf { it.weight }}")
}