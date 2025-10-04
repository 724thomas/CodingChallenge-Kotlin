package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class OrderedStream(n: Int) {
    val values = Array<String?>(n+2) { null }
    var idx = 1

    fun insert(idKey: Int, value: String): List<String> {
        values[idKey] = value
        val result = mutableListOf<String>()

        while (values[idx] != null) {
            result += values[idx++]!!
        }
        return result
    }

}

/**
 * Your OrderedStream object will be instantiated and called as such:
 * var obj = OrderedStream(n)
 * var param_1 = obj.insert(idKey,value)
 */