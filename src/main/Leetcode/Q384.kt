package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution(private val nums: IntArray) {
    private val original = nums.copyOf()

    fun reset(): IntArray = original.copyOf()

    fun shuffle(): IntArray {
        val arr = nums.copyOf()
        for (i in arr.indices.reversed()) {
            val j = Random.nextInt(i + 1) // [0..i]
            arr[i] = arr[j].also { arr[j] = arr[i] } // swap
        }
        return arr
    }
}