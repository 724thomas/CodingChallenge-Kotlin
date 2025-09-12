package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Solution {
    fun minimumAddedInteger(nums1: IntArray, nums2: IntArray): Int {
        nums1.sort()
        nums2.sort()
        var ans = Int.MAX_VALUE

        for (i in 0 until minOf(3, nums1.size)) {
            val x = nums2[0] - nums1[i]
            var j = 0
            var removed = 0
            for (num in nums1) {
                if (j < nums2.size && num + x == nums2[j]) {
                    j++
                } else {
                    removed++
                }
            }
            if (removed <= 2) {
                ans = minOf(ans, x)
            }
        }
        return ans
    }
}
