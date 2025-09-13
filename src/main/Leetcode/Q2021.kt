package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun brightestPosition(lights: Array<IntArray>): Int {
        var cmin = Int.MAX_VALUE
        var cmax = Int.MIN_VALUE

        val prefix = sortedMapOf<Int, Int>()

        for ((idx, range) in lights) {
            cmin = min(cmin, idx - range)
            cmax = max(cmax, idx + range)

            val l = idx - range
            val r = idx + range + 1
            prefix[l] = (prefix[l] ?: 0) + 1
            prefix[r] = (prefix[r] ?: 0) - 1
        }

        var ans = cmin
        var bright = 0
        var curr = 0

        for ((pos, delta) in prefix) {
            curr += delta
            if (curr > bright) {
                bright = curr
                ans = pos
            }
        }

        return ans
    }
}
