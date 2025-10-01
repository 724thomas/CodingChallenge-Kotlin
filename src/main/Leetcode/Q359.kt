package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Logger() {
    val pq = PriorityQueue<Pair<String, Int>>(compareBy { it.second })
    val visited = mutableSetOf<String>()

    fun shouldPrintMessage(timestamp: Int, message: String): Boolean {
        while (pq.isNotEmpty() && pq.peek().second <= timestamp-10) {
            visited.remove(pq.poll().first)
        }

        val ans = message !in visited
        if (ans) {
            pq.add(message to timestamp)
            visited.add(message)
        }

        return ans
    }
}

/**
 * Your Logger object will be instantiated and called as such:
 * var obj = Logger()
 * var param_1 = obj.shouldPrintMessage(timestamp,message)
 */