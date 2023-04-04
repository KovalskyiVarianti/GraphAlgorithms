private fun Graph.kruskal(): List<Edge> {
    val edgesByWeight = vertices.flatMap { it.edges }.sortedBy { it.weight }
    val result = mutableListOf<Edge>()
    val parent = IntArray(vertices.count()) { it }
    edgesByWeight.forEach { edge ->
        val root1 = find(edge.source, parent)
        val root2 = find(edge.destination, parent)
        if (root1 != root2) {
            result.add(edge)
            parent[root1] = root2
        }
    }
    return result
}

private fun find(vertex: Int, parent: IntArray): Int {
    var v = vertex
    while (v != parent[v]) {
        parent[v] = parent[parent[v]]
        v = parent[v]
    }
    return v
}

fun main() {
    val graph = readGraph("src/main/kotlin/1.txt")
    println(graph)
    val result = graph.kruskal()
    result.forEach { edge ->
        println("${edge.source} - ${edge.destination} : ${edge.weight}")
    }
}