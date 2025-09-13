package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun valueAfterKSeconds(n: Int, k: Int): Int {
        val MOD = 1_000_000_007
        val arr = IntArray(n)
        for (i in 0..n-1) arr[i] = 1

        for (i in 0..k-1) {
            for (j in 1..n-1) {
                arr[j] = (arr[j] + arr[j-1]) % MOD
            }
        }

        return arr[n-1]
    }
}