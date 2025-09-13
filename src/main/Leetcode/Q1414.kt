package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun findMinFibonacciNumbers(k: Int): Int {
        val fibs = mutableListOf(1, 1)
        while (fibs.last() < k) {
            val n = fibs.size
            fibs.add( fibs[n-1] + fibs[n-2])
        }

        var remain = k
        var count = 0
        var i = fibs.size - 1

        while (remain > 0) {
            if (fibs[i] <= remain) {
                remain -= fibs[i]
                count++
            } else {
                i --
            }
        }
        return count
    }
}