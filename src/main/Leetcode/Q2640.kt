package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun findPrefixScore(nums: IntArray): LongArray {
        val ans = mutableListOf<Long>()
        var cmax = 0L
        var total = 0L
        for (num in nums) {
            val longNum = num.toLong()
            cmax = max(cmax, longNum)
            total += longNum + cmax
            ans.add(total)
        }

        return ans.toLongArray()
    }
}
