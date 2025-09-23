package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun buildArray(nums: IntArray): IntArray {
        val n = nums.size
        val ans = IntArray(n)
        for (i in 0..n-1) {
            ans[i] = nums[nums[i]]
        }
        return ans
    }
}