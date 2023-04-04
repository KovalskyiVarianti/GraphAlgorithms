import java.util.*

fun readTestGraph(path: String) : Graph {
    print("Put test file name [1. 2 or 3]: ")
    val fileName = Scanner(System.`in`).nextLine()
    return readGraph("$path$fileName.txt").also {graph ->
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
        println("----------------------------------------------")
    }
}