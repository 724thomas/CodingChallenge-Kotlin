package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun makePrefSumNonNegative(nums: IntArray): Int {
        var sum: Long = 0
        var ans: Int = 0
        val pq = PriorityQueue<Int>()

        for (num in nums) {
            sum += num
            if (num < 0) pq.offer(num)

            if (sum < 0) {
                val remove = pq.poll()
                sum -= remove
                ans++
            }
        }
        return ans
    }
}