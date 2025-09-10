package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */

import java.math.*

class Solution {
    fun minMoves(nums: IntArray): Int {
        val n = nums.size
        var min = nums[0]
        var ans = 0
        for (i in 1 until n) min = Math.min(min, nums[i])
        for (i in 0 until n) ans+= Math.abs(nums[i] - min)
        return ans
    }
}