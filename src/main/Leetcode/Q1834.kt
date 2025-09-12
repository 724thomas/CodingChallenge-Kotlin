package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
data class Pair (val start: Int, val processingTime: Int, val index: Int)

class Solution {
    fun getOrder(tasks: Array<IntArray>): IntArray {
        val n = tasks.size

        val taskList = tasks.mapIndexed { i, t ->
            Pair(t[0], t[1], i)
        }.sortedBy { it.start }

        val result = IntArray(n)
        var idx = 0
        var i = 0
        var time = 0L

        val pq = PriorityQueue<Pair>(compareBy<Pair> { it.processingTime }.thenBy {it.index})

        while (idx < n) {
            while (i < n && taskList[i].start <= time) {
                pq.offer(taskList[i])
                i++
            }

            if (pq.isNotEmpty()) {
                val cur = pq.poll()
                result[idx++] = cur.index
                time += cur.processingTime
            } else {
                time = taskList[i].start.toLong()
            }
        }
        return result
    }
}