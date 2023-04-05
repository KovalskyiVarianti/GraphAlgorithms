import java.io.File
import java.io.FileNotFoundException
import java.util.*

const val DIVIDER = "----------------------------------------------"

fun readTestGraph(path: String) : Graph {
    val files = File(path).listFiles()
        ?.filter { it.extension == "txt" }
        ?.joinToString(prefix = "[", postfix = "]") { it.name }
        ?: throw FileNotFoundException("There is no test data!")

    print("Put test file name without extension $files: ")
    val fileName = Scanner(System.`in`).nextLine()
    return readGraph("$path$fileName.txt").also {graph ->
        println("Graph")
        println("Adj matrix:")
        println(graph.adjMatrix.joinToString("\n"))
        println(DIVIDER)
        println(
            graph.vertices.joinToString("\n") { vertex ->
                "Vertex ${vertex.number + 1} edges: ${vertex.edges.joinToString(" ") {
                    "[${it.source + 1}--(${it.weight})-->${it.destination + 1}]" }
                }"
            }
        )
        println(DIVIDER)
    }
}