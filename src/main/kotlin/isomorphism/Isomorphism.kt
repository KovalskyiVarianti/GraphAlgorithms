package isomorphism

import DIVIDER
import Graph
import readGraph

private infix fun Graph.isomorphic(other: Graph): Boolean {
    if (vertices.count() != other.vertices.count()) return false

    val thisDegrees = vertices.map { it.edges.count() }
    val otherDegrees = other.vertices.map { it.edges.count() }.toMutableList()

    thisDegrees.forEach {
        if (otherDegrees.remove(it).not()) {
            return false
        }
    }

    return otherDegrees.isEmpty()
}


fun main() {
    val firstGraph = readGraph("src/main/kotlin/cpp/1.txt")
    val secondGraph = readGraph("src/main/kotlin/cpp/2.txt")
    println("First graph:")
    println(firstGraph.adjMatrix.joinToString("\n"))
    println(DIVIDER)
    println("Second graph:")
    println(secondGraph.adjMatrix.joinToString("\n"))
    println(DIVIDER)
    println("These graphs ${if (firstGraph isomorphic secondGraph) "may be" else "are definitely not"} isomorphic.")
}