fun Graph.findEulerPath(): List<Int> {
    require(isEuler().not()) { "Graph doesn't have an Euler path" }
    val path = mutableListOf<Int>()
    val startVertex = vertices.first()

    fun findEulerPathRecursive(vertex: Vertex) {
        while (vertex.edges.isNotEmpty()) {
            val edge = vertex.edges.removeFirst()
            val adjacentVertex = vertices[edge.destination]
            adjacentVertex.edges.removeIf { it.destination == vertex.number }
            findEulerPathRecursive(adjacentVertex)
        }
        path.add(vertex.number)
    }

    findEulerPathRecursive(startVertex)
    return path.reversed()
}

fun Graph.isEuler() = vertices.all { it.edges.size % 2 == 0 }

fun main() {

}