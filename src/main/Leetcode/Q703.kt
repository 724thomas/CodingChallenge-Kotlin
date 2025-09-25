package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class KthLargest(private val k: Int, nums: IntArray) {
    private val minHeap = PriorityQueue<Int>()

    init {
        for (num in nums) {
            add(num)
        }
    }
    fun add(value: Int): Int {
        minHeap.add(value)
        if (minHeap.size > k) {
            minHeap.poll()
        }
        return minHeap.peek()
    }

}

/**
 * Your KthLargest object will be instantiated and called as such:
 * var obj = KthLargest(k, nums)
 * var param_1 = obj.add(`val`)
 */