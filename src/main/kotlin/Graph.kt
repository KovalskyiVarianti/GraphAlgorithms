import java.io.File

data class Graph(val vertices: List<Vertex>, val adjMatrix: List<List<Int>>)
data class Vertex(val number: Int, val edges: MutableList<Edge>)
data class Edge(val source: Int, val destination: Int, val weight: Int)

fun readGraph(filePath: String): Graph {
    val file = File(filePath).also { println(it.absolutePath) }
    val lines = file.readLines()
    val adjMatrix = lines.map { line ->
        line.split(",").mapNotNull { it.trim().toIntOrNull() }
    }
    val vertices = adjMatrix.mapIndexed { source, row ->
        val edges = mutableListOf<Edge>()
        row.forEachIndexed { destination, weight ->
            if (weight > 0) {
                edges.add(Edge(source, destination, weight))
            }
        }
        Vertex(source, edges)
    }
    return Graph(vertices, adjMatrix)
}