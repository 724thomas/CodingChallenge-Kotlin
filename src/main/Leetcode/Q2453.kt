package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun destroyTargets(nums: IntArray, space: Int): Int {
        val counter = mutableMapOf<Int, Int>()
        val minMap = mutableMapOf<Int, Int>()

        for (num in nums) {
            val mod = num % space
            counter[mod] = counter.getOrDefault(mod, 0) + 1
            minMap[mod] = minOf(minMap.getOrDefault(mod, num), num)
        }

        var ans = Int.MAX_VALUE
        var maxCount = 0
        for ((mod, count) in counter) {
            if (count > maxCount) {
                maxCount = count
                ans = minMap[mod]!!
            } else if (count == maxCount) {
                ans = minOf(ans, minMap[mod]!!)
            }
        }

        return ans
    }
}
