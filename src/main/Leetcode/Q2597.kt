package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {

    fun beautifulSubsets(nums: IntArray, k: Int): Int {
        nums.sort()
        var count = 0

        fun dfs(idx: Int, chosen: MutableMap<Int, Int>) {
            if (idx == nums.size) {
                if (chosen.isNotEmpty()) count++
                return
            }

            dfs(idx+1, chosen)

            val curr = nums[idx]
            if ((curr-k) !in chosen) {
                chosen[curr] = (chosen[curr] ?: 0) + 1
                dfs(idx+1, chosen)
                chosen[curr] = chosen[curr]!! - 1
                if (chosen[curr] == 0) chosen.remove(curr)
            }
        }

        dfs(0, mutableMapOf())
        return count
    }
}