package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Main {
    class Solution {
        lateinit var arr: IntArray
        var n: Int = 0

        fun getNextIdx(idx: Int, amount: Int): Int? {
            if (idx+amount < 0 || idx+amount >=n) return null
            return idx + amount
        }

        fun canReach(arr: IntArray, start: Int): Boolean {
            this.arr = arr
            n = arr.size

            val deque: ArrayDeque<Int> = ArrayDeque()
            deque.add(start)

            while (deque.isNotEmpty()) {
                val currIdx = deque.removeFirst()
                val currVal = arr[currIdx]
                if (arr[currIdx] == -1) continue
                if (arr[currIdx] == 0) return true

                arr[currIdx] = -1

                getNextIdx(currIdx, currVal)?.let { deque.add(it) }
                getNextIdx(currIdx, -currVal)?.let { deque.add(it) }
            }

            return false
        }
    }

}