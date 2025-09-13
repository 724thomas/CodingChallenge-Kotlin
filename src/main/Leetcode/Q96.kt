package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun numTrees(n: Int): Int {
        val dp = IntArray(n+1)
        dp[0] = 1
        dp[1] = 1

        for (totalNodes in 2..n) {
            var total = 0
            for (root in 1..totalNodes) {
                total += dp[root-1] * dp[totalNodes - root]
            }
            dp[totalNodes] = total
        }
        return dp[n]
    }
}