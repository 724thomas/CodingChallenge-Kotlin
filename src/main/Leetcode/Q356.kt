package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Main {
    class Solution {
        fun isReflected(points: Array<IntArray>): Boolean {
            val pointMap = mutableMapOf<Int, MutableSet<Int>>()
            for ((x, y) in points) {
                pointMap[x] = pointMap.getOrDefault(x, mutableSetOf())
                pointMap[x]!!.add(y)
            }

            for ((x, ySet) in pointMap) {
                if (!pointMap.containsKey(-x)) return false
            }
            return true
        }
    }

}