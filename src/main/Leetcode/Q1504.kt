package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun numSubmat(mat: Array<IntArray>): Int {
        val n = mat.size
        val m = mat[0].size
        val height = IntArray(m)
        var ans = 0

        for (i in 0 until n) {
            for (j in 0 until m) {
                if (mat[i][j] == 0) height[j] = 0
                else height[j] += 1
            }

            for (j in 0 until m) {
                var minHeight = Int.MAX_VALUE
                for (k in j downTo 0) {
                    minHeight = minOf(minHeight, height[k])
                    if (minHeight == 0) break
                    ans += minHeight
                }
            }
        }
        return ans
    }
}