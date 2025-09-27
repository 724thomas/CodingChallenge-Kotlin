package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class NumArray(nums: IntArray) {
    private val prefixSum: IntArray = nums.runningFold(0, Int::plus).toIntArray()

    fun sumRange(left: Int, right: Int): Int =
        prefixSum[right+1] - prefixSum[left]
}

/**
 * Your NumArray object will be instantiated and called as such:
 * var obj = NumArray(nums)
 * var param_1 = obj.sumRange(left,right)
 */