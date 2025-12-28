import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun measured(
    func: () -> Any
) {
    var result: Any
    measureTimeMillis { result = func() }.also {
        println("$result (${it}ms)")
    }
}

fun measuredNano(
    func: () -> Any
) {
    var result: Any
    measureNanoTime { result = func() }.also {
        println("$result (${it/1000000.0}ms)")
    }
}