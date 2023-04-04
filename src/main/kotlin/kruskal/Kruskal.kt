package kruskal

import Edge
import Graph
import readGraph
import java.util.Scanner

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
    print("Put test file name [1. 2 or 3]: ")
    val fileName = Scanner(System.`in`).nextLine()
    val graph = readGraph("src/main/kotlin/kruskal/$fileName.txt")
    println("Graph")
    println("Adj matrix:")
    println(graph.adjMatrix.joinToString("\n"))
    println("----------------------------------------------")
    println(
        graph.vertices.joinToString("\n") { vertex ->
            "Vertex ${vertex.number + 1} edges: ${vertex.edges.joinToString(" ") { 
                "[${it.source + 1}--(${it.weight})-->${it.destination + 1}]" }
            }"
        }
    )
    val result = graph.kruskal()
    println("----------------------------------------------")
    println("Min spanning tree (Kruskal algorithm)")
    result.forEach { edge ->
        println("[${edge.source + 1}--(${edge.weight})-->${edge.destination + 1}]")
    }
    println("Total weight: ${result.sumOf { it.weight }}")
}