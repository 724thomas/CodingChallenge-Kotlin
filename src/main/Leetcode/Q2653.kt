package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun getSubarrayBeauty(nums: IntArray, k: Int, x: Int): IntArray {
        val n = nums.size
        val result = IntArray(n - k + 1)
        val freq = IntArray(101)  // [-50,50] → [0..100]로 매핑

        fun idx(num: Int) = num + 50

        // 초기 윈도우
        for (i in 0 until k) {
            val value = nums[i]
            freq[idx(value)]++
        }

        // beauty 계산 함수
        fun getBeauty(): Int {
            var cnt = 0
            for (v in -50..-1) {   // 음수만 확인
                cnt += freq[idx(v)]
                if (cnt >= x) return v
            }
            return 0
        }

        result[0] = getBeauty()

        // 슬라이딩
        for (i in k until n) {
            freq[idx(nums[i])]++        // 새로 들어온 값
            freq[idx(nums[i - k])]--    // 빠진 값
            result[i - k + 1] = getBeauty()
        }

        return result
    }
}
