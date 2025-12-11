import kotlin.system.measureTimeMillis

fun measured(
    func: () -> Any
) {
    var result: Any
    measureTimeMillis { result = func() }.also {
        println("$result (${it}ms)")
    }
}