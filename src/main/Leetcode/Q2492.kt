package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
import kotlin.math.min
import java.util.*

class Solution {
    fun minScore(n: Int, roads: Array<IntArray>): Int {
        // 인접 리스트 (도시 -> (이웃, 거리))
        val graph = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
        for ((u, v, d) in roads) {
            graph.getOrPut(u) { mutableListOf() }.add(v to d)
            graph.getOrPut(v) { mutableListOf() }.add(u to d)
        }

        val visited = BooleanArray(n + 1)
        val queue: Queue<Int> = LinkedList()
        queue.add(1)
        visited[1] = true

        var ans = Int.MAX_VALUE

        while (queue.isNotEmpty()) {
            val cur = queue.poll()
            for ((next, d) in graph[cur] ?: emptyList()) {
                ans = min(ans, d)         // 간선 거리 최소값 업데이트
                if (!visited[next]) {
                    visited[next] = true
                    queue.add(next)
                }
            }
        }

        return ans
    }
}
