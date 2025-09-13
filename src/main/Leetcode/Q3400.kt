package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun maximumMatchingIndices(nums1: IntArray, nums2: IntArray): Int {
        val n = nums1.size
        val nums = IntArray(n*2)

        for (i in 0..n-1) {
            nums[i] = nums1[i]
            nums[i+n] = nums1[i]
        }

        var ans: Int = 0
        for (i in 0..n-1) {
            var count: Int = 0;
            for (j in 0..n-1) {
                if (nums[i+j] == nums2[j]) count++
            }
            ans = max(ans, count)
        }


        return ans
    }
}