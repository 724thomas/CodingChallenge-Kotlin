package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun countCompleteDayPairs(hours: IntArray): Long {
        val counter = mutableMapOf<Int, Long>()
        var ans = 0L

        for (h in hours) {
            val r = h%24
            val need = (24-r) % 24
            ans += counter.getOrDefault(need, 0L)
            counter[r] = counter.getOrDefault(r, 0L) +1
        }
        return ans
    }
}