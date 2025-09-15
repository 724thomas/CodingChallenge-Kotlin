package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun findCommonResponse(responses: List<List<String>>): String {
        val responseSet = mutableMapOf<Int, MutableSet<String>>()

        for (i in 0..responses.size-1) {
            responseSet.putIfAbsent(i, mutableSetOf())
            for (r in responses[i]) responseSet[i]!!.add(r)
        }

        val counter = mutableMapOf<String, Int>()
        var count = 0
        var ans: String? = null
        for (i in 0..responses.size-1) {
            for (r in responseSet[i]!!) {
                counter[r] = counter.getOrDefault(r, 0)+1

                when {
                    counter[r]!! > count -> {
                        count = counter[r]!!
                        ans = r
                    }
                    counter[r] == count -> {
                        ans = if (ans == null) r else minOf(ans!!, r)
                    }
                }
            }
        }
        return ans ?: ""
    }
}