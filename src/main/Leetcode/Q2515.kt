package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Main {
    class Solution {
        var n: Int = 0

        fun getPrev(index: Int): Int {
            return (index - 1 + n) % n
        }

        fun getNext(index: Int): Int {
            return (index + 1) % n
        }

        fun closestTarget(words: Array<String>, target: String, startIndex: Int): Int {
            this.n = words.size

            var ans: Int = 0
            var leftIdx: Int = startIndex
            var rightIdx: Int = startIndex

            repeat(n) {
                var leftString: String = words[leftIdx]
                var rightString: String = words[rightIdx]

                if (leftString == target || rightString == target) return ans

                leftIdx = getPrev(leftIdx)
                rightIdx = getNext(rightIdx)
                ans++
            }

            return -1
        }
    }

}