package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    private val graph = mutableMapOf<Int, MutableList<Int>>()
    private lateinit var cost: IntArray
    private val visited = mutableSetOf<Int>()

    private fun dfs(node: Int): Pair<Int, Long> { // (changes, maxPathSum)
        visited.add(node)
        val children = graph[node] ?: emptyList()

        var changes = 0
        var maxPath = 0L
        var maxCount = 0
        var childCount = 0

        for (child in children) {
            if (child in visited) continue
            val (childChanges, childPath) = dfs(child)
            changes += childChanges
            childCount++

            when {
                childPath > maxPath -> {
                    maxPath = childPath
                    maxCount = 1
                }
                childPath == maxPath -> {
                    maxCount++
                }
            }
        }

        changes += childCount - maxCount
        return changes to (maxPath + cost[node].toLong())
    }

    fun minIncrease(n: Int, edges: Array<IntArray>, cost: IntArray): Int {
        this.cost = cost
        for ((u, v) in edges) {
            graph.getOrPut(u) { mutableListOf() }.add(v)
            graph.getOrPut(v) { mutableListOf() }.add(u)
        }
        return dfs(0).first
    }
}
