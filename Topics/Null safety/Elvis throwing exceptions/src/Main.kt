

fun main() {
    val str: String? = readLine() // you need to add something
    println("Elvis says: ${str?: throw IllegalStateException()}")
}