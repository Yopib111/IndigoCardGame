// do not change this data class
data class Box(val height: Int, val length: Int, val width: Int)


fun main() {
    val a = readln().toInt()
    val b = readln().toInt()
    val c = readln().toInt()
    val d = readln().toInt()
    val box = Box(a, b, d)
    val box1 = box.copy(length = c)
    println(box)
    println(box1)

    // implement your code here

}