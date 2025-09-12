package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Solution {
    fun minimumSum(nums: IntArray): Int {
        // nums[i] < nums[j] > nums[k]
        val n = nums.size
        val lPrefix = IntArray(n)
        val rPrefix = IntArray(n)
        var ans = Int.MAX_VALUE

        lPrefix[0] = nums[0]
        rPrefix[n-1] = nums[n-1]

        for (i in 1 until n) {
            lPrefix[i] = Math.min(lPrefix[i-1], nums[i])
            rPrefix[n-1-i] = Math.min(rPrefix[n-i], nums[n-1-i])
        }

        for (i in 1 until n-1) {
            val mid = nums[i]
            val left = lPrefix[i-1]
            val right = rPrefix[i+1]

            if (left < mid && mid > right) {
                ans = Math.min(ans, left + mid + right)
            }
        }

        return if (ans == Int.MAX_VALUE) -1 else ans
    }
}